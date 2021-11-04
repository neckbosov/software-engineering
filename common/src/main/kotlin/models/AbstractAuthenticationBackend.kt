package models

import models.auth.EmailPasswordCreds
import models.auth.GoogleAuthStep
import models.auth.Jwt

interface AbstractAuthenticationBackend {
    fun registerViaEmailPassword(creds: EmailPasswordCreds, profileType: ProfileType): Jwt
    fun loginViaEmailPassword(creds: EmailPasswordCreds): Jwt

    fun registerViaGoogle(profileType: ProfileType): GoogleAuthStep
    fun postRegisterViaGoogle(token: String): Jwt

    fun loginViaGoogle(): GoogleAuthStep
    fun postLoginViaGoogle(token: String): Jwt
}