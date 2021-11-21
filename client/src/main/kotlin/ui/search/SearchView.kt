package ui.search

import MenuBar
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Search
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import kotlinx.coroutines.launch
import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile
import ui.SimpleAppInfo
import ui.profile.view.ProfileViewState
import ui.utils.BoxWithVerticalScroll
import ui.utils.PictureView
import ui.utils.loadNetworkImage

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchStudentView(appInfo: SimpleAppInfo, profile: StudentProfile, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    var studentId: Long? = null
    scope.launch {
        studentId = appInfo.client.getIdByEmail(profile.email)
    }
    ListItem(
        text = { Text(profile.surname + " " + profile.name + " " + profile.patronymic) },
        icon = {
            val picture = profile.avatarURL?.let { loadNetworkImage(profile.avatarURL ?: "") }
            PictureView(
                picture,
                modifier = Modifier
                    .background(Color.Transparent)
                    .clip(CircleShape)
                    .height(50.dp)
                    .border(
                        1.dp,
                        Color.DarkGray,
                        CircleShape
                    )
            )
        },
        secondaryText = {
            val universityInfo = profile.universityDescription
            Text("Student in ${universityInfo.universityName}, ${universityInfo.faculty}, ${universityInfo.course}")
        },
        trailing = { Text("Student") },
        modifier = Modifier.fillMaxWidth().clickable {
            if (studentId != null) {
                appInfo.currentState.value = ProfileViewState(studentId!!)
            }
        }
    )
}

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun SearchTutorView(appInfo: SimpleAppInfo, profile: InstructorProfile, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    var tutorId: Long? = null
    scope.launch {
        tutorId = appInfo.client.getIdByEmail(profile.email)
    }
    ListItem(
        text = { Text(profile.surname + " " + profile.name + " " + profile.patronymic) },
        icon = {
            val picture = profile.avatarURL?.let { loadNetworkImage(profile.avatarURL ?: "") }
            PictureView(
                picture,
                modifier = Modifier
                    .background(Color.Transparent)
                    .clip(CircleShape)
                    .height(50.dp)
                    .border(
                        1.dp,
                        Color.DarkGray,
                        CircleShape
                    )
            )
        },
        secondaryText = { Text(profile.degree) },
        trailing = { Text("Tutor") },
        modifier = Modifier.fillMaxWidth().clickable {
            if (tutorId != null) {
                appInfo.currentState.value = ProfileViewState(tutorId!!)
            }
        }
    )
}

@Composable
fun SearchView(appInfo: SimpleAppInfo, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val searchResult = mutableStateOf(listOf<UserProfile>())

    val text = mutableStateOf(TextFieldValue(""))
    val tutorsFlag = mutableStateOf(true)
    val studentsFlag = mutableStateOf(true)

    MenuBar(appInfo) {
        BoxWithVerticalScroll {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(modifier = Modifier.padding(10.dp).fillMaxWidth()) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                val tags = text.value.text.split(" ").toList()
                                val students = if (studentsFlag.value) { appInfo.client.searchStudentsByTags(tags) } else { listOf() }
                                val tutors = if (tutorsFlag.value) {appInfo.client.searchInstructorsByTags(tags) } else { listOf() }
                                searchResult.value = students + tutors
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Search, "Search")
                    }
                    OutlinedTextField(
                        text.value,
                        onValueChange = {
                            text.value = it
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
                Row(horizontalArrangement = Arrangement.spacedBy(3.dp), modifier = Modifier.padding(start = 10.dp).fillMaxWidth()) {
                    Text("Options: ")
                    Text("Tutors:")
                    Checkbox(
                        tutorsFlag.value,
                        onCheckedChange = {
                            tutorsFlag.value = it
                        }
                    )
                    Text("Students:")
                    Checkbox(
                        studentsFlag.value,
                        onCheckedChange = {
                            studentsFlag.value = it
                        }
                    )
                }
                Spacer(modifier = Modifier.height(5.dp))
                Column(
                    modifier = modifier
                ) {
                    if (searchResult.value.isEmpty()) {
                        Text(":(")
                    } else {
                        val tutors = searchResult.value.filterIsInstance<InstructorProfile>()
                        tutors.forEach {
                            Divider()
                            SearchTutorView(appInfo, it)
                        }
                        val students = searchResult.value.filterIsInstance<StudentProfile>()
                        students.forEach {
                            Divider()
                            SearchStudentView(appInfo, it)
                        }
                    }
                }
            }
        }
    }
}
