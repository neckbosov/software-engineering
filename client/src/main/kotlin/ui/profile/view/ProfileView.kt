package ui.profile.view

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

@Composable
fun ProfileView(appInfo: SimpleAppInfo) {
    val scope = rememberCoroutineScope()
    var user by remember { mutableStateOf<UserProfile?>(null) }
    scope.launch {
        user = appInfo.client.getProfile(appInfo.currentId!!)
    }
    when (val profile = user) {
        is StudentProfile -> {
            StudentProfileView(appInfo, profile, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
        }
        is InstructorProfile -> {
            InstructorProfileView(appInfo, profile, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
        }
        else -> {
           Text("Loading")
        }
    }
}
