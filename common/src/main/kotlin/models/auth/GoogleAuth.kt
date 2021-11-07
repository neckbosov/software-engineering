package models.auth

import kotlinx.serialization.Serializable

@Serializable
data class GoogleAuthStep(val authURI: String, val token: String)