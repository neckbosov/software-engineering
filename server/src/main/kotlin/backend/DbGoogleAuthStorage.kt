package backend

import auth.GoogleCredentials
import db.dao.*
import db.dao.auth.AppId2PersonId
import db.dao.auth.TemporaryAuthTokens
import models.ProfileType
import models.auth.AbstractGoogleAuthStorage
import org.jetbrains.exposed.dao.id.LongIdTable
import org.jetbrains.exposed.sql.*
import org.jetbrains.exposed.sql.transactions.transaction
import java.sql.Timestamp
import java.time.Instant
import java.util.Date.from

class DbGoogleAuthStorage: AbstractGoogleAuthStorage {

    init {
        transaction {
            SchemaUtils.create(TemporaryAuthTokens, AppId2PersonId)
        }
    }

    override fun setRegister(token: String, type: ProfileType, creds: GoogleCredentials?) {
        transaction {
            val data = TemporaryAuthTokens.select {TemporaryAuthTokens.temporaryToken.eq(token)}.toList()
            if (data.isNotEmpty()) {
                TemporaryAuthTokens.update ({TemporaryAuthTokens.temporaryToken.eq(token)}) {
                    it[TemporaryAuthTokens.accessToken] = creds?.accessToken
                    it[TemporaryAuthTokens.refreshToken] = creds?.refreshToken
                    it[TemporaryAuthTokens.expiresAt] = creds?.expiresAt?.toInstant()
                    it[TemporaryAuthTokens.profileType] = type
                    it[TemporaryAuthTokens.timestamp] = Instant.now()
                }
            } else {
                TemporaryAuthTokens.insert {
                    it[TemporaryAuthTokens.temporaryToken] = token
                    it[TemporaryAuthTokens.accessToken] = creds?.accessToken
                    it[TemporaryAuthTokens.refreshToken] = creds?.refreshToken
                    it[TemporaryAuthTokens.expiresAt] = creds?.expiresAt?.toInstant()
                    it[TemporaryAuthTokens.profileType] = type
                    it[TemporaryAuthTokens.timestamp] = Instant.now()
                }
            }
        }
    }

    override fun getRegister(token: String): Pair<ProfileType, GoogleCredentials?>? {
        return transaction {
            val data = TemporaryAuthTokens.select { TemporaryAuthTokens.temporaryToken.eq(token) }.toList()[0]
            val profileType = data[TemporaryAuthTokens.profileType]
            val accessToken = data[TemporaryAuthTokens.accessToken]
            val refreshToken = data[TemporaryAuthTokens.refreshToken]
            val expiresAt = data[TemporaryAuthTokens.expiresAt]?.let { Timestamp.from(it) }
            if (accessToken == null || refreshToken == null || expiresAt == null) {
                Pair(profileType, null)
            } else {
                Pair(profileType, GoogleCredentials(accessToken, refreshToken, expiresAt))
            }
        }
    }

    override fun registerContains(token: String): Boolean {
        return transaction {
            val data = TemporaryAuthTokens.select {TemporaryAuthTokens.temporaryToken.eq(token)}.toList()
            data.isNotEmpty()
        }
    }

    override fun setLogin(token: String, creds: GoogleCredentials?) {
        transaction {

            val data = TemporaryAuthTokens.select {TemporaryAuthTokens.temporaryToken.eq(token)}.toList()
            if (data.isNotEmpty()) {
                TemporaryAuthTokens.update ({TemporaryAuthTokens.temporaryToken.eq(token)}) {
                    it[TemporaryAuthTokens.accessToken] = creds?.accessToken
                    it[TemporaryAuthTokens.refreshToken] = creds?.refreshToken
                    it[TemporaryAuthTokens.expiresAt] = creds?.expiresAt?.toInstant()
                    it[TemporaryAuthTokens.timestamp] = Instant.now()
                }
            } else {
                TemporaryAuthTokens.insert {
                    it[TemporaryAuthTokens.temporaryToken] = token
                    it[TemporaryAuthTokens.accessToken] = creds?.accessToken
                    it[TemporaryAuthTokens.refreshToken] = creds?.refreshToken
                    it[TemporaryAuthTokens.expiresAt] = creds?.expiresAt?.toInstant()
                    it[TemporaryAuthTokens.profileType] = it[TemporaryAuthTokens.profileType]
                    it[TemporaryAuthTokens.timestamp] = Instant.now()
                }
            }


        }
    }

    override fun getLogin(token: String): GoogleCredentials? {
        return transaction {
            val data = TemporaryAuthTokens.select { TemporaryAuthTokens.temporaryToken.eq(token) }.toList()[0]
            val accessToken = data[TemporaryAuthTokens.accessToken]
            val refreshToken = data[TemporaryAuthTokens.refreshToken]
            val expiresAt = data[TemporaryAuthTokens.expiresAt]?.let { Timestamp.from(it) }
            if (accessToken == null || refreshToken == null || expiresAt == null) {
                null
            } else {
                GoogleCredentials(accessToken, refreshToken, expiresAt)
            }
        }
    }

    override fun loginContains(token: String): Boolean {
        return transaction {
            val data = TemporaryAuthTokens.select {TemporaryAuthTokens.temporaryToken.eq(token)}.toList()
            data.isNotEmpty()
        }
    }

    override fun setApp2ProfileId(appId: String, profileId: Long) {
        transaction {
            AppId2PersonId.insert {
                it[AppId2PersonId.appId] = appId
                it[AppId2PersonId.profileId] = profileId
            }
        }
    }

    override fun getProfileIdByApp(appId: String): Long? {
        return transaction {
            val data = AppId2PersonId.select { AppId2PersonId.appId.eq(appId) }.toList()
            if (data.isEmpty()) {
                null
            } else {
                data[0][AppId2PersonId.profileId]
            }
        }
    }

    override fun appIdContains(appId: String): Boolean {
        return transaction {
            val data = AppId2PersonId.select {AppId2PersonId.appId.eq(appId)}.toList()
            data.isNotEmpty()
        }
    }

}