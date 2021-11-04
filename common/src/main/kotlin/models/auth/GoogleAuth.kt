package models.auth

import kotlinx.serialization.Serializable
import java.net.URL

@Serializable
data class GoogleAuthStep(val authUrl: String, val token: String)