package backend

import api.AbstractAuthenticationAPI
import auth.GoogleApi
import auth.GoogleAppCredentials
import auth.GoogleCredentials
import auth.GoogleOAuthHandler
import db.dao.Instructors
import db.dao.Profiles
import db.dao.Students
import kotlinx.coroutines.delay
import models.ProfileType
import models.auth.*
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

@Suppress("RemoveRedundantQualifierName")
class SimpleAuthenticationAPI(val jwtInstance: SimpleJwt, googleCredentials: GoogleAppCredentials) :
    AbstractAuthenticationAPI {
    private val emailPasswordAuthStorage = EmailPasswordAuthStorage()
    private val googleAuthStorage = GoogleAuthStorage()

    private val googleOAuthHandler = GoogleOAuthHandler(googleCredentials) { state, creds ->
        if (state == null) {
            println("unknown google oauth with null state: ${creds}")
            return@GoogleOAuthHandler
        }
        if (googleAuthStorage.registerIntermediateStep.containsKey(state)) {
            val data = googleAuthStorage.registerIntermediateStep[state]
            if (data == null) {
                println("unknown google oauth register state auth: ${state} -> ${creds}") // debug
                return@GoogleOAuthHandler
            }
            googleAuthStorage.registerIntermediateStep[state] = Pair(data.first, creds)
        } else if (googleAuthStorage.loginIntermediateStep.containsKey(state)) {
            googleAuthStorage.loginIntermediateStep[state] = creds
        } else {
            println("unknown state: ${state} -> ${creds}")
            return@GoogleOAuthHandler
        }
    }

    class AuthException(desc: String) : RuntimeException(desc) {}

    override suspend fun registerViaEmailPassword(creds: EmailPasswordCredentials, profileType: ProfileType): Jwt {
        val account = emailPasswordAuthStorage.get(creds.email)
        if (account != null) {
            throw AuthException("email is already taken")
        }
        return transaction {
            val profileId = Profiles.insert {
                it[Profiles.email] = creds.email
                it[Profiles.isActive] = false
                it[Profiles.firstName] = "FirstName"
                it[Profiles.lastName] = "LastName"
                it[Profiles.profileType] = profileType
            }.resultedValues!![0][Profiles.id].value
            mkProfileTypeRecord(profileId, profileType)
            emailPasswordAuthStorage.set(creds.email, creds.password, profileId)
            jwtInstance.sign(UserClaims(profileId))
        }
    }

    override suspend fun loginViaEmailPassword(creds: EmailPasswordCredentials): Jwt {
        val account = emailPasswordAuthStorage.get(creds.email) ?: throw RuntimeException("no such account")
        if (account.first != creds.password) {
            throw AuthException("invalid password")
        }
        return jwtInstance.sign(UserClaims(account.second))
    }

    override suspend fun registerViaGoogle(profileType: ProfileType): GoogleAuthStep {
        val state = makeRandomToken()
        googleAuthStorage.registerIntermediateStep[state] = Pair(profileType, null)
        return GoogleAuthStep(googleOAuthHandler.makeOAuthURI(state).toString(), state)
    }

    override suspend fun postRegisterViaGoogle(token: String): Jwt {
        if (!googleAuthStorage.registerIntermediateStep.containsKey(token)) {
            throw AuthException("no such token found in the registry")
        }
        for (i in 1..100000) {
            if (googleAuthStorage.registerIntermediateStep[token]!!.second == null) {
                delay(50)
            } else {
                break
            }
        }
        if (googleAuthStorage.registerIntermediateStep[token]!!.second == null) {
            throw AuthException("register timeout")
        }
        val stepResult = googleAuthStorage.registerIntermediateStep[token]!!
        val creds = stepResult.second!!
        val userInfo = GoogleApi(creds).userInfo()
        if (googleAuthStorage.googleAppId2ProfileId.containsKey(userInfo.appId)) {
            throw AuthException("account already exists")
        }
        return transaction {
            val profileId = Profiles.insert {
                it[Profiles.email] = userInfo.email
                it[Profiles.isActive] = false
                it[Profiles.firstName] = userInfo.name
                it[Profiles.lastName] = ""
                it[Profiles.profileType] = stepResult.first
            }.resultedValues!![0][Profiles.id].value
            mkProfileTypeRecord(profileId, stepResult.first)
            googleAuthStorage.googleAppId2ProfileId[userInfo.appId] = profileId
            jwtInstance.sign(UserClaims(profileId))
        }
    }

    override suspend fun loginViaGoogle(): GoogleAuthStep {
        val state = makeRandomToken()
        googleAuthStorage.loginIntermediateStep[state] = null
        return GoogleAuthStep(googleOAuthHandler.makeOAuthURI(state).toString(), state)
    }

    override suspend fun postLoginViaGoogle(token: String): Jwt {
        if (!googleAuthStorage.loginIntermediateStep.containsKey(token)) {
            throw AuthException("no such token found in the registry")
        }
        for (i in 1..100000) {
            if (googleAuthStorage.loginIntermediateStep[token] == null) {
                delay(50)
            } else {
                break
            }
        }
        if (googleAuthStorage.loginIntermediateStep[token] == null) {
            throw AuthException("login timeout")
        }
        val creds = googleAuthStorage.loginIntermediateStep[token]!!
        val userInfo = GoogleApi(creds).userInfo()
        val profileId = googleAuthStorage.googleAppId2ProfileId[userInfo.appId]
            ?: throw AuthException("no profile registered for specified google account")
        return jwtInstance.sign(UserClaims(profileId))
    }

    private fun makeRandomToken(length: Int = 64): String {
        val alphabet = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM"
        return (1..length)
            .map { _ -> kotlin.random.Random.nextInt(0, alphabet.length) }
            .map(alphabet::get)
            .joinToString("")
    }

    private fun Transaction.mkProfileTypeRecord(profileId: Long, profileType: ProfileType) {
        when (profileType) {
            ProfileType.Student -> Students.insert {
                it[Students.profileId] = profileId
                it[Students.university] = ""
                it[Students.faculty] = ""
                it[Students.degree] = ""
                it[Students.course] = 1
                it[Students.from] = ""
                it[Students.to] = ""
            }
            ProfileType.Instructor -> Instructors.insert {
                it[Instructors.profileId] = profileId
                it[Instructors.degree] = ""
            }
        }
    }

    private class EmailPasswordAuthStorage {
        val storage = mutableMapOf<String, Pair<String, Long>>() // email -> (password, profile_id)

        fun set(email: String, password: String, profileId: Long) {
            storage[email] = Pair(password, profileId)
        }

        fun get(email: String): Pair<String, Long>? {
            return storage[email]
        }
    }

    private class GoogleAuthStorage {
        val registerIntermediateStep = mutableMapOf<String, Pair<ProfileType, GoogleCredentials?>>()
        val loginIntermediateStep = mutableMapOf<String, GoogleCredentials?>()
        val googleAppId2ProfileId = mutableMapOf<String, Long>()
    }
}