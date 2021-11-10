package ui

import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import backend.SimpleBackend
import db.SimpleDatabaseImpl
import ui.authorization.AuthorizationState
import ui.models.UIState

class SimpleAppInfo {
    val currentState: MutableState<UIState> = mutableStateOf(AuthorizationState())
    var currentId: Long? = null
    val backend = SimpleBackend(SimpleDatabaseImpl)
}