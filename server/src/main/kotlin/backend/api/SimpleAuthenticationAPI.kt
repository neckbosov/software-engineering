package backend.api

import auth.GoogleApi
import auth.GoogleAppCredentials
import auth.GoogleOAuthHandler
import backend.storage.BasicEmailAuthStorage
import backend.storage.DbGoogleAuthStorage
import db.dao.Instructors
import db.dao.Profiles
import db.dao.Students
import error.AuthException
import kotlinx.coroutines.delay
import models.AbstractAuthenticationAPI
import models.ProfileType
import models.auth.*
import org.jetbrains.exposed.sql.Transaction
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.transactions.transaction

class SimpleAuthenticationAPI(val jwtInstance: SimpleJwt, googleCredentials: GoogleAppCredentials) :
        AbstractAuthenticationAPI {
    private val emailPasswordAuthStorage = BasicEmailAuthStorage()
//    private val googleAuthStorage = BasicGoogleAuthStorage()
    private val googleAuthStorage = DbGoogleAuthStorage()

    private val googleOAuthHandler = GoogleOAuthHandler(googleCredentials) { state, creds ->
        if (state == null) {
            println("unknown google oauth with null state: ${creds}")
            return@GoogleOAuthHandler
        }
        if (googleAuthStorage.registerContains(state)) {
            val data = googleAuthStorage.getRegister(state)
            if (data == null) {
                println("unknown google oauth register state auth: ${state} -> ${creds}") // debug
                return@GoogleOAuthHandler
            }
            googleAuthStorage.setRegister(state, data.first, creds)
        } else if (googleAuthStorage.loginContains(state)) {
            googleAuthStorage.setLogin(state, creds)
        } else {
            println("unknown state: ${state} -> ${creds}")
            return@GoogleOAuthHandler
        }
    }

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
        googleAuthStorage.setRegister(state, profileType, null)
        return GoogleAuthStep(googleOAuthHandler.makeOAuthURI(state).toString(), state)
    }

    override suspend fun postRegisterViaGoogle(token: String): Jwt {

        if(!googleAuthStorage.registerContains(token)) {
            throw AuthException("no such token found in the registry")
        }
        for (i in 1..100000) {
            if (googleAuthStorage.getRegister(token)!!.second == null) {
                delay(50)
            } else {
                break
            }
        }
        if (googleAuthStorage.getRegister(token)!!.second == null) {
            throw AuthException("register timeout")
        }
        val stepResult = googleAuthStorage.getRegister(token)!!
        val creds = stepResult.second!!
        val userInfo = GoogleApi(creds).userInfo()
        if (googleAuthStorage.appIdContains(userInfo.appId)) {
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
            googleAuthStorage.setApp2ProfileId(userInfo.appId, profileId)
            jwtInstance.sign(UserClaims(profileId))
        }
    }

    override suspend fun loginViaGoogle(): GoogleAuthStep {
        val state = makeRandomToken()
        googleAuthStorage.setLogin(state, null)
        return GoogleAuthStep(googleOAuthHandler.makeOAuthURI(state).toString(), state)
    }

    override suspend fun postLoginViaGoogle(token: String): Jwt {

        if(!googleAuthStorage.loginContains(token)) {
            throw AuthException("no such token found in the registry")
        }
        for (i in 1..100000) {
            if (googleAuthStorage.getLogin(token) == null) {
                delay(50)
            } else {
                break
            }
        }
        if (googleAuthStorage.getLogin(token) == null) {
            throw AuthException("login timeout")
        }
        val creds = googleAuthStorage.getLogin(token)!!
        val userInfo = GoogleApi(creds).userInfo()
        val profileId = googleAuthStorage.getProfileIdByApp(userInfo.appId)
            ?: throw AuthException("no profile registered for specified google account")
        return jwtInstance.sign(UserClaims(profileId))
    }

    private fun makeRandomToken(length: Int = 64): String {
        val alphabet = "0123456789QWERTYUIOPASDFGHJKLZXCVBNM"
        return (1..length)
            .map { _ -> kotlin.random.Random.nextInt(0, alphabet.length) }
            .map(alphabet::get)
            .joinToString("");
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
}