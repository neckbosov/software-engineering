package ui.profile.edit

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.profile.*
import ui.DummyAppInfo

@Composable
@Preview
fun ProfileEdit(appInfo: DummyAppInfo) {
    when (appInfo.currentProfile.value) {
        null -> {}
        is StudentProfile -> {
            StudentProfileEdit(appInfo, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
        }
        is InstructorProfile -> {
            InstructorProfileEdit(appInfo, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
        }
    }
}