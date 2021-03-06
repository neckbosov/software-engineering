package ui.profile.edit

import MenuBar
import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile
import ui.SimpleAppInfo
import ui.profile.edit.models.TMPInstructorProfileEdit
import ui.profile.edit.models.TMPStudentProfileEdit

@Composable
@Preview
fun ProfileEdit(appInfo: SimpleAppInfo) {
    val modifier = Modifier.padding(start = 10.dp, top = 10.dp)
    val scope = rememberCoroutineScope()
    var user by remember { mutableStateOf<UserProfile?>(null) }
    scope.launch {
        user = appInfo.client.getProfile(appInfo.currentId!!)
    }
    MenuBar(appInfo) {
        when (val x = user) {
            is StudentProfile -> {
                StudentProfileEdit(
                    appInfo,
                    TMPStudentProfileEdit(x),
                    modifier = modifier
                )
            }
            is InstructorProfile -> {
                InstructorProfileEdit(
                    appInfo,
                    TMPInstructorProfileEdit(x),
                    modifier = modifier
                )
            }
            else -> {
                Text("Loading")
            }
        }
    }
}