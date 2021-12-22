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
fun AutoCompleteText(
    value: TextFieldValue,
    onValueChange: (TextFieldValue) -> Unit,
    onOptionSelected: (String) -> Unit,
    modifier: Modifier = Modifier,
    //label: @Composable (() -> Unit)? = null,
    suggestions: List<String> = emptyList()
) {
    Column(modifier = modifier) {
        OutlinedTextField(
            value = value,
            onValueChange = { text -> if (text !== value) onValueChange(text) },
            modifier = Modifier.fillMaxWidth(),
            //label = label,
        )
        DropdownMenu(
            expanded = suggestions.isNotEmpty(),
            onDismissRequest = {  },
            modifier = Modifier.fillMaxWidth(),
            focusable = false,
        ) {
            suggestions.forEach { label ->
                DropdownMenuItem(onClick = {
                    onOptionSelected(label)
                }) {
                    Text(text = label)
                }
            }
        }

    }
}



@Composable
fun SearchView(appInfo: SimpleAppInfo, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()
    val searchResult = mutableStateOf(listOf<UserProfile>())

    val text = remember { mutableStateOf(TextFieldValue("")) }
    val tutorsFlag = mutableStateOf(true)
    val studentsFlag = mutableStateOf(true)

    val tags = remember { text.value.text.split(" ").toList().toMutableStateList() }

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
                                searchResult.value = students + tutors
                            }
                        }
                    ) {
                        Icon(Icons.Filled.Search, "Search")
                    }
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
                    val suggestionsLastTag = remember { mutableStateListOf<String>() }
                    scope.launch {
                        suggestionsLastTag.clear()
                        suggestionsLastTag.addAll(appInfo.client.getTagsByPrefix(lastTag))
                    }
                    val suggestions = suggestionsLastTag.map { "$previousTags $it" }
                    AutoCompleteText(
                        value = text.value,
                        onValueChange = { text.value = it },
                        onOptionSelected = { text.value = TextFieldValue(it) },
                        modifier = Modifier.fillMaxWidth(),
                        suggestions = suggestions
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
