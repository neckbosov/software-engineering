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
    when (val profile = appInfo.backend.getProfile(appInfo.currentId!!)) {
        is StudentProfile -> {
            StudentProfileView(appInfo, profile, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
        }
        is InstructorProfile -> {
            InstructorProfileView(appInfo, profile, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
        }
    }
}
