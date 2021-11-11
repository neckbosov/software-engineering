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
import models.profile.StudentProfile
import ui.SimpleAppInfo
import ui.profile.edit.ProfileEditState
import ui.utils.BoxWithVerticalScroll

@Composable
@Preview
fun StudentProfileView(appInfo: SimpleAppInfo, profileId: Long, profile: StudentProfile, modifier: Modifier = Modifier) {
    BoxWithVerticalScroll(modifier = modifier.fillMaxSize(1f)) {
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Box(modifier = Modifier.fillMaxWidth(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    ImageView(profile.avatarURL)
                    StudentProfileInfoView(profile)
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
            CVView(profile)
        }
    }
}

@Composable
@Preview
fun StudentProfileInfoView(profile: StudentProfile, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        NameView(profile)
        EmailView(profile.email)
        val universityInfo = profile.universityDescription
        Text(
            text = "Student in ${universityInfo.universityName}, ${universityInfo.faculty}, ${universityInfo.course}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Text(
            text = "Grade: ${universityInfo.grade}",
            style = TextStyle(
                fontSize = 15f.sp,
                fontWeight = FontWeight.SemiBold,
            )
        )
        Text(
            text = "GPA: ${universityInfo.gpa}",
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
fun CVView(profile: StudentProfile, modifier: Modifier = Modifier) {
    Row(modifier) {
        Text(
            text = "CV URL: ",
            style = TextStyle(
                fontSize = 20f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Text(
            text = profile.cvURL ?: "Not loaded",
            fontSize = 20f.sp
        )
    }
}