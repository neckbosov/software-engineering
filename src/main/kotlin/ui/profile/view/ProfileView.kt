package ui.profile.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ui.DummyAppInfo

@Composable
fun ProfileView(appInfo: DummyAppInfo) {
    if (appInfo.isStudent) {
        appInfo.currentProfile.value = appInfo.backend.getStudentProfile(appInfo.currentId)
        StudentProfileView(appInfo, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
    } else {
        appInfo.currentProfile.value = appInfo.backend.getInstructorProfile(appInfo.currentId)
        InstructorProfileView(appInfo, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
    }
}
