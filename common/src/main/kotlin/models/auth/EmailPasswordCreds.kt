package models.auth

import kotlinx.serialization.Serializable

@Serializable
data class EmailPasswordCreds(val email: String, val password: String)