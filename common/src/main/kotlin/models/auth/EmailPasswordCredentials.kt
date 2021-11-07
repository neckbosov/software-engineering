package models.auth

import kotlinx.serialization.Serializable

@Serializable
data class EmailPasswordCredentials(val email: String, val password: String)