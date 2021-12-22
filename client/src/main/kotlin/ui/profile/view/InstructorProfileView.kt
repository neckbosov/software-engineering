package ui.profile.view

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.profile.InstructorProfile
import ui.SimpleAppInfo
import ui.profile.edit.ProfileEditState
import ui.utils.BoxWithVerticalScroll

@Composable
@Preview
fun InstructorProfileView(
    appInfo: SimpleAppInfo,
    profileId: Long,
    profile: InstructorProfile,
    modifier: Modifier = Modifier
) {
    Box(modifier = modifier.fillMaxWidth(1f)) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.fillMaxWidth(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    ImageView(profile.avatarURL)
                    InstructorProfileInfoView(profile)
                }
                if (profileId == appInfo.currentId) {
                    IconButton(
                        onClick = { appInfo.currentState.value = ProfileEditState() },
                        modifier = Modifier.align(Alignment.TopEnd)
                    ) {
                        Icon(Icons.Filled.Edit, "edit")
                    }
                }
            }
            Spacer(modifier = Modifier.height(5.dp))
            AchievmentsView(profile.achievements)
            CarrierView(profile.career)
            WorksView(profile)
        }
    }
}

@Composable
@Preview
fun InstructorProfileInfoView(profile: InstructorProfile, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        NameView(profile)
        EmailView(profile.email)
        Text(
            text = "Tutor in ${profile.degree}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        TagsView(profile.interestsTags)
    }
}

@Composable
@Preview
fun WorksView(profile: InstructorProfile, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Text(
            text = "Works:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        profile.works.forEachIndexed { i, work ->
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                Text(
                    text = "$i)",
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
                Text(
                    text = work.name,
                    fontSize = 20f.sp,
                    fontWeight = FontWeight.SemiBold,
                )
            }
            Text(text = work.description)
            Text(
                text = "Details: ${work.detailsURL}",
                fontWeight = FontWeight.SemiBold,
            )
        }
    }
}