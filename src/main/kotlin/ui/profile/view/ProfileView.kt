package ui.profile.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.profile.InstructorProfile
import models.profile.StudentProfile
import ui.DummyAppInfo

@Composable
fun ProfileView(appInfo: DummyAppInfo) {
    when (val x = appInfo.currentProfile.value) {
        null -> { }
        is StudentProfile -> {
            appInfo.currentProfile.value = appInfo.backend.getStudentProfile(appInfo.currentId.toLong())
            StudentProfileView(appInfo, x, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
        }
        is InstructorProfile -> {
            appInfo.currentProfile.value = appInfo.backend.getInstructorProfile(appInfo.currentId.toLong())
            InstructorProfileView(appInfo, x, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
        }
    }
}
