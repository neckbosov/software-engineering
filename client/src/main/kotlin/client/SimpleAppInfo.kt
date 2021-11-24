package ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import client.HTTPProfileClient
import client.createClient
import models.auth.Jwt
import models.auth.SimpleJwt
import ui.authorization.AuthorizationState
import ui.models.UIState

class SimpleAppInfo {
    val currentState: MutableState<UIState> = mutableStateOf(AuthorizationState())
    var currentJwt: Jwt? = null
    val currentId: Long?
        get() {
            return currentJwt?.let { SimpleJwt.parse(it).id }
        }

    val client = HTTPProfileClient(createClient(), "http://0.0.0.0:8080/v0", { currentJwt })
}
