package models

import models.auth.EmailPasswordCredentials
import models.auth.GoogleAuthStep
import models.auth.Jwt

interface AbstractAuthenticationBackend {
    fun registerViaEmailPassword(creds: EmailPasswordCredentials, profileType: ProfileType): Jwt
    fun loginViaEmailPassword(creds: EmailPasswordCredentials): Jwt

    fun registerViaGoogle(profileType: ProfileType): GoogleAuthStep
    suspend fun postRegisterViaGoogle(token: String): Jwt

    fun loginViaGoogle(): GoogleAuthStep
    suspend fun postLoginViaGoogle(token: String): Jwt
}