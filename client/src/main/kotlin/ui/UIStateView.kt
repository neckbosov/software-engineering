package ui

import androidx.compose.runtime.Composable
import client.SimpleAppInfo
import ui.authorization.Authorization
import ui.authorization.AuthorizationState
import ui.profile.edit.ProfileEdit
import ui.profile.edit.ProfileEditState
import ui.profile.view.ProfileView
import ui.profile.view.ProfileViewState

@Composable
fun UIStateView(appInfo: SimpleAppInfo) {
    when (appInfo.currentState.value) {
        is AuthorizationState -> {
            Authorization(appInfo)
        }
        is ProfileViewState -> {
            ProfileView(appInfo)
        }
        is ProfileEditState -> {
            ProfileEdit(appInfo)
        }
    }
}