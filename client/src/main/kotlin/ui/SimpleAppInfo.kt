package ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import auth.GoogleAppCredentials
import backend.SimpleAuthenticationBackend
import backend.SimpleProfileBackend
import models.auth.Jwt
import models.auth.SimpleJwt
import ui.authorization.AuthorizationState
import ui.models.UIState

class SimpleAppInfo {
    val currentState: MutableState<UIState> = mutableStateOf(AuthorizationState())
    var currentJwt: Jwt? = null
    val currentId: Long?
        get() {
            val jwt = currentJwt ?: return null
            return jwtInstance.decode(jwt).id
        }

    // backend-related things
    val jwtInstance = SimpleJwt("aboba") // TODO: parse secret from some local file
    val profileBackend = SimpleProfileBackend()
    val authBackend = SimpleAuthenticationBackend(
        jwtInstance,
        GoogleAppCredentials("135814761571-ojbn8ij88grgktaekq12hkmk78h9k3ri.apps.googleusercontent.com", "NcZnNeZL8J-7pMXQgkz5zFiX") // it seems okay to put these secrets here
    )
}