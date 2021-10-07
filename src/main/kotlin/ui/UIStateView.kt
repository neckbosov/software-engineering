package ui

import androidx.compose.runtime.Composable
import ui.authorization.Authorization
import ui.authorization.AuthorizationState
import ui.profile.edit.ProfileEdit
import ui.profile.edit.ProfileEditState
import ui.profile.view.ProfileView
import ui.profile.view.ProfileViewState

@Composable
fun UIStateView(appInfo: DummyAppInfo) {
    when (appInfo.currentState.value) {
        is AuthorizationState -> { Authorization(appInfo) }
        is ProfileViewState -> { ProfileView(appInfo) }
        is ProfileEditState -> { ProfileEdit(appInfo) }
    }
}