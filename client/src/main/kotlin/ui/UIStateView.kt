package ui

import androidx.compose.runtime.Composable
import ui.authorization.Authorization
import ui.authorization.AuthorizationState
import ui.profile.create.ProfileCreate
import ui.profile.create.ProfileCreateState
import ui.profile.edit.ProfileEdit
import ui.profile.edit.ProfileEditState
import ui.profile.view.ProfileView
import ui.profile.view.ProfileViewState

@Composable
fun UIStateView(appInfo: SimpleAppInfo) {
    when (val state = appInfo.currentState.value) {
        is AuthorizationState -> {
            Authorization(appInfo)
        }
        is ProfileCreateState -> {
            ProfileCreate(appInfo)
        }
        is ProfileViewState -> {
            ProfileView(appInfo, state.profileId)
        }
        is ProfileEditState -> {
            ProfileEdit(appInfo)
        }
    }
}