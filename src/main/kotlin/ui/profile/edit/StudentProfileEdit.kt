package ui.profile.edit

import ui.profile.view.*

import androidx.compose.desktop.ui.tooling.preview.Preview
import androidx.compose.foundation.layout.*
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Cancel
import androidx.compose.material.icons.filled.Done
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import models.profile.StudentProfile
import ui.DummyAppInfo
import ui.profile.edit.models.TMPStudentProfileEdit
import ui.utils.BoxWithVerticalScroll

@Composable
@Preview
fun StudentProfileEdit(appInfo: DummyAppInfo, modifier: Modifier = Modifier) {
    val profile = TMPStudentProfileEdit(appInfo.currentProfile.value as StudentProfile)
    BoxWithVerticalScroll(modifier = modifier.fillMaxSize(1f)) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Box(modifier = Modifier.fillMaxWidth(1f)) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    ImageEdit(profile.avatarURL)
                    Column {
                        NameEdit(profile)
                        TagsEdit(profile.interestsTags)
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = {
                            appInfo.backend.postStudentProfile(appInfo.currentId, profile.toStudentProfile())
                            appInfo.currentState.value = ProfileViewState()
                        }
                    ) {
                        Icon(Icons.Filled.Done, "save")
                    }
                    IconButton(
                        onClick = { appInfo.currentState.value = ProfileViewState() }
                    ) {
                        Icon(Icons.Filled.Cancel, "close")
                    }
                }
            }
            StudentProfileInfoEdit(profile)
            AchievmentsEdit(profile.achievements)
            CarrierEdit(profile.career)
            CVEdit(profile)
        }
    }
}

@Composable
@Preview
fun StudentProfileInfoEdit(profile: TMPStudentProfileEdit, modifier: Modifier = Modifier) {
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Text(
            text = "University Info:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        val universityInfo = profile.universityDescription
        Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
            OutlinedTextField(
                value = universityInfo.universityName.value,
                onValueChange = { universityInfo.universityName.value = it },
                label = { Text("University name") },
                modifier = Modifier.width(200.dp)
            )
            OutlinedTextField(
                value = universityInfo.faculty.value,
                onValueChange = { universityInfo.faculty.value = it },
                label = { Text("Faculty") },
                modifier = Modifier.widthIn(200.dp)
            )
            OutlinedTextField(
                value = universityInfo.grade.value,
                onValueChange = { universityInfo.grade.value = it },
                label = { Text("Grade") },
                modifier = Modifier.widthIn(100.dp)
            )
            OutlinedTextField(
                value = universityInfo.gpa.value,
                onValueChange = { universityInfo.gpa.value = it },
                label = { Text("GPA") },
                modifier = Modifier.widthIn(100.dp)
            )
        }
        TagsEdit(profile.interestsTags)
    }
}

@Composable
@Preview
fun CVEdit(profile: TMPStudentProfileEdit, modifier: Modifier = Modifier) {
    Text(
        text = "CV:",
        style = TextStyle(
            fontSize = 25f.sp,
            fontWeight = FontWeight.SemiBold,
        ),
    )
    OutlinedTextField(
        value = profile.cvURL.value,
        onValueChange = { profile.cvURL.value = it },
        label = { Text("CV URL") },
        modifier = modifier.widthIn(100.dp, 300.dp)
    )
}