package backend

import auth.GoogleCredentials
import models.ProfileType
import models.auth.AbstractGoogleAuthStorage


class BasicGoogleAuthStorage : AbstractGoogleAuthStorage {
    private val registerIntermediateStep = mutableMapOf<String, Pair<ProfileType, GoogleCredentials?>>()  // randomToken -> (profType, creds)
    private val loginIntermediateStep = mutableMapOf<String, GoogleCredentials?>()  // randomToken -> creds
    private val googleAppId2ProfileId = mutableMapOf<String, Long>()  // userInfo.appId -> profileId

    override fun setRegister(token: String, type: ProfileType, creds: GoogleCredentials?) {
        registerIntermediateStep[token] = Pair(type, creds)
    }

    override fun getRegister(token: String): Pair<ProfileType, GoogleCredentials?>? {
        return registerIntermediateStep[token]
    }

    override fun registerContains(token: String): Boolean {
        return registerIntermediateStep.containsKey(token)
    }

    override fun setLogin(token: String, creds: GoogleCredentials?) {
        loginIntermediateStep[token] = creds
    }

    override fun getLogin(token: String): GoogleCredentials? {
        return loginIntermediateStep[token]
    }

    override fun loginContains(token: String): Boolean {
        return loginIntermediateStep.containsKey(token)
    }

    override fun setApp2ProfileId(appId: String, profileId: Long) {
        googleAppId2ProfileId[appId] = profileId
    }

    override fun getProfileIdByApp(appId: String): Long? {
        return googleAppId2ProfileId[appId]
    }

    override fun appIdContains(appId: String): Boolean {
        return googleAppId2ProfileId.containsKey(appId)
    }

}