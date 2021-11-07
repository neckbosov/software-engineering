package ui.profile.edit

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.profile.InstructorProfile
import models.profile.StudentProfile
import ui.SimpleAppInfo
import ui.profile.edit.models.TMPInstructorProfileEdit
import ui.profile.edit.models.TMPStudentProfileEdit

@Composable
@Preview
fun ProfileEdit(appInfo: SimpleAppInfo) {
    when (val x = appInfo.profileBackend.getProfile(appInfo.currentId!!)) {
        is StudentProfile -> {
            StudentProfileEdit(
                appInfo,
                TMPStudentProfileEdit(x),
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )
        }
        is InstructorProfile -> {
            InstructorProfileEdit(
                appInfo,
                TMPInstructorProfileEdit(x),
                modifier = Modifier.padding(start = 10.dp, top = 10.dp)
            )
        }
    }
}