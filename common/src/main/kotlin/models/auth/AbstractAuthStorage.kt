package models.auth

import auth.GoogleCredentials
import models.ProfileType

interface AbstractEmailAuthStorage {
    fun set(email: String, password: String, profileId: Long)
    fun get(email: String): Pair<String, Long>?
}

interface AbstractGoogleAuthStorage {
    fun setRegister(token: String, type: ProfileType, creds: GoogleCredentials?)
    fun getRegister(token: String): Pair<ProfileType, GoogleCredentials?>?
    fun registerContains(token: String): Boolean

    fun setLogin(token: String, creds: GoogleCredentials?)
    fun getLogin(token: String): GoogleCredentials?
    fun loginContains(token: String): Boolean

    fun setApp2ProfileId(appId: String, profileId: Long)
    fun getProfileIdByApp(appId: String): Long?
    fun appIdContains(appId: String): Boolean
}