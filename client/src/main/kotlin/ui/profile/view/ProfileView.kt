package ui.profile.view

import MenuBar
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
fun ProfileView(appInfo: SimpleAppInfo, profileId: Long) {
    val scope = rememberCoroutineScope()
    var user by remember { mutableStateOf<UserProfile?>(null) }
    scope.launch {
        user = appInfo.client.getProfile(profileId)
    }
    MenuBar(appInfo) {
        when (val profile = user) {
            is StudentProfile -> {
                StudentProfileView(appInfo, profileId, profile, modifier = Modifier.padding(start = 10.dp, top = 10.dp))
                ReviewsView(profile)
            }
            is InstructorProfile -> {
                InstructorProfileView(
                    appInfo,
                    profileId,
                    profile,
                    modifier = Modifier.padding(start = 10.dp, top = 10.dp)
                )
                ReviewsView(profile)
            }
            else -> {
                Text("Loading")
            }
        }
    }
}
