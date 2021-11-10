package ui.profile.view

import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import models.profile.InstructorProfile
import models.profile.StudentProfile
import client.SimpleAppInfo
import kotlinx.coroutines.launch
import models.profile.UserProfile

@Composable
fun ProfileView(appInfo: SimpleAppInfo) {
    val scope = rememberCoroutineScope()
    var user = remember<UserProfile?> { null }
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
    }
}
