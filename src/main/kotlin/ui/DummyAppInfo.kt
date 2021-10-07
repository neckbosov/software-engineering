package ui

import DummyBackend
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableStateOf
import models.profile.UserProfile
import ui.models.UIState
import ui.profile.edit.ProfileEditState
import ui.profile.view.ProfileViewState

class DummyAppInfo {
    val currentState: MutableState<UIState> = mutableStateOf(ProfileViewState())
    var currentId = 0
    val backend = DummyBackend()
    val currentProfile: MutableState<UserProfile?> = mutableStateOf(backend.getProfile(1))
}