package ui

import DummyBackend
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import models.profile.UserProfile
import ui.authorization.AuthorizationState
import ui.models.UIState
import ui.profile.edit.ProfileEditState
import ui.profile.view.ProfileViewState

class DummyAppInfo {
    val currentState: MutableState<UIState> = mutableStateOf(AuthorizationState())
    var currentId: Long? = null
    val backend = DummyBackend() // TODO("Replace")
}