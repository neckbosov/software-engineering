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
import androidx.compose.runtime.*
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
                    .width(50.dp)
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
                    .width(50.dp)
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
    val searchResult = remember {  mutableStateListOf<UserProfile>() }

    val text = remember { mutableStateOf(TextFieldValue("")) }

    val tutorsFlag = remember {  mutableStateOf(true) }
    val studentsFlag = remember {  mutableStateOf(true) }

    val tags = remember { text.value.text.split(" ").filter { it.isNotBlank() }.toMutableStateList() }
    val suggestionsLastTag = remember { mutableStateListOf<String>() }
    val suggestions = remember { mutableStateListOf<String>() }
    val expandedFlag = remember { mutableStateOf(suggestions.isNotEmpty()) }

    val updateText: (TextFieldValue, Boolean) -> Unit = { it, closeFlag ->
        text.value = it
        tags.clear()
        tags.addAll(text.value.text.split(" ").filter { it.isNotBlank() })
        val lastTag = if (tags.isNotEmpty()) {
            tags.last()
        } else {
            ""
        }
        val previousTags = if (tags.isNotEmpty()) {
            tags.take(tags.size - 1)
        } else {
            emptyList()
        }.joinToString(" ")
        if (lastTag.isNotBlank()) {
            scope.launch {
                suggestionsLastTag.clear()
                suggestionsLastTag.addAll(appInfo.client.getTagsByPrefix(lastTag))
                suggestions.clear()
                suggestions.addAll(suggestionsLastTag.map { "$previousTags $it" })
                expandedFlag.value = if (closeFlag) false else suggestions.isNotEmpty()
            }
        }
    }

    MenuBar(appInfo) {
        BoxWithVerticalScroll {
            Column(verticalArrangement = Arrangement.spacedBy(5.dp)) {
                Row(modifier = Modifier.padding(10.dp).fillMaxWidth()) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                val students = if (studentsFlag.value) {
                                    appInfo.client.searchStudentsByTags(tags)
                                } else {
                                    listOf()
                                }
                                val tutors = if (tutorsFlag.value) {
                                    appInfo.client.searchInstructorsByTags(tags)
                                } else {
                                    listOf()
                                }
                                searchResult.clear()
                                searchResult.addAll(students + tutors)
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Search, "Search")
                    }

                    Column(modifier = modifier) {
                        OutlinedTextField(
                            value = text.value,
                            onValueChange = { txt ->
                                updateText(txt, false)
                            },
                            modifier = Modifier.fillMaxWidth(),
                            //label = label,
                        )
                        DropdownMenu(
                            expanded = expandedFlag.value,
                            onDismissRequest = {
                                expandedFlag.value = false
                                               },
                            modifier = Modifier.width(500.dp),
                            focusable = false,
                        ) {
                            suggestions.forEach { label ->
                                DropdownMenuItem(onClick = {
                                    updateText(TextFieldValue(label), true)
                                }) {
                                    Text(text = label)
                                }
                            }
                        }
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(3.dp),
                    modifier = Modifier.padding(start = 10.dp).fillMaxWidth()
                ) {
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
                    if (searchResult.isEmpty()) {
                        Text(":(")
                    } else {
                        val tutors = searchResult.filterIsInstance<InstructorProfile>()
                        tutors.forEach {
                            Divider()
                            SearchTutorView(appInfo, it)
                        }
                        val students = searchResult.filterIsInstance<StudentProfile>()
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
