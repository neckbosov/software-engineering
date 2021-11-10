package models

import models.auth.EmailPasswordCredentials
import models.auth.GoogleAuthStep
import models.auth.Jwt

interface AbstractAuthenticationAPI {
    suspend fun registerViaEmailPassword(creds: EmailPasswordCredentials, profileType: ProfileType): Jwt
    suspend fun loginViaEmailPassword(creds: EmailPasswordCredentials): Jwt

    suspend fun registerViaGoogle(profileType: ProfileType): GoogleAuthStep
    suspend fun postRegisterViaGoogle(token: String): Jwt

    suspend fun loginViaGoogle(): GoogleAuthStep
    suspend fun postLoginViaGoogle(token: String): Jwt
}