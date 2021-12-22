package ui.profile.edit

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
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlinx.coroutines.launch
import ui.SimpleAppInfo
import ui.profile.edit.models.TMPStudentProfileEdit
import ui.profile.view.ProfileViewState
import ui.utils.BoxWithVerticalScroll
import ui.utils.OutlinedTextFieldWithError

@Composable
@Preview
fun StudentProfileEdit(appInfo: SimpleAppInfo, profile: TMPStudentProfileEdit, modifier: Modifier = Modifier) {
    val scope = rememberCoroutineScope()

    BoxWithVerticalScroll(modifier = modifier.fillMaxSize(1f)) {
        Column(verticalArrangement = Arrangement.spacedBy(15.dp)) {
            Box(modifier = Modifier.fillMaxWidth()) {
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.align(Alignment.TopStart)
                ) {
                    ImageEdit(profile.avatarURL)
                    Column(
                        modifier = Modifier.width(500.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        NameEdit(profile)
                        StudentProfileInfoEdit(profile)
                    }
                }
                Row(
                    horizontalArrangement = Arrangement.spacedBy(10.dp),
                    modifier = Modifier.align(Alignment.TopEnd)
                ) {
                    IconButton(
                        onClick = {
                            scope.launch {
                                appInfo.client.updateStudentProfile(appInfo.currentId!!, profile.toStudentProfile())
                                appInfo.currentState.value = ProfileViewState(appInfo.currentId!!)
                            }
                        },
                        enabled = profile.validToSubmit.value
                    ) {
                        Icon(Icons.Filled.Done, "Save button")
                    }
                    IconButton(
                        onClick = { appInfo.currentState.value = ProfileViewState(appInfo.currentId!!) }
                    ) {
                        Icon(Icons.Filled.Cancel, "Close button")
                    }
                }
            }
            TagsEdit(profile.interestsTags)
            AchievmentsEdit(profile.achievements)
            CarrierEdit(profile.career)
            CVEdit(profile)
        }
    }
}

val checkBigDecimal: (String) -> Boolean = { it.isBlank() || it.toBigDecimalOrNull() != null }
val checkInt: (String) -> Boolean = { it.isBlank() || it.toIntOrNull() != null }

@Composable
@Preview
fun StudentProfileInfoEdit(profile: TMPStudentProfileEdit, modifier: Modifier = Modifier) {
    val universityInfo = profile.universityDescription
    val isGPAValid = remember { mutableStateOf(checkBigDecimal(universityInfo.gpa.value)) }
    val isCourseValid = remember { mutableStateOf(checkInt(universityInfo.course.value)) }
    val isGradeValid = remember { mutableStateOf(checkInt(universityInfo.grade.value)) }

    profile.validToSubmit.value = isGPAValid.value && isCourseValid.value && isGradeValid.value
    Column(verticalArrangement = Arrangement.spacedBy(10.dp), modifier = modifier) {
        Text(
            text = "University Info:",
            style = TextStyle(
                fontSize = 25f.sp,
                fontWeight = FontWeight.SemiBold,
            ),
        )
        Column(verticalArrangement = Arrangement.spacedBy(10.dp)) {
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                OutlinedTextField(
                    value = universityInfo.universityName.value,
                    onValueChange = { universityInfo.universityName.value = it },
                    label = { Text("University name") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
                OutlinedTextField(
                    value = universityInfo.faculty.value,
                    onValueChange = { universityInfo.faculty.value = it },
                    label = { Text("Faculty") },
                    singleLine = true,
                    modifier = Modifier.weight(1f)
                )
            }
            Row(horizontalArrangement = Arrangement.spacedBy(5.dp)) {
                OutlinedTextFieldWithError(
                    value = universityInfo.grade.value,
                    onValueChange = {
                        isGradeValid.value = checkInt(it)
                        universityInfo.grade.value = it
                    },
                    label = { Text("Grade") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    isError = !isGradeValid.value,
                    errorMessage = { Text("Grade should be a number") }
                )
                OutlinedTextFieldWithError(
                    value = universityInfo.course.value,
                    onValueChange = {
                        isCourseValid.value = checkInt(it)
                        universityInfo.course.value = it
                    },
                    label = { Text("Course") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    isError = !isCourseValid.value,
                    errorMessage = { Text("Course should be a number") }
                )
                OutlinedTextFieldWithError(
                    value = universityInfo.gpa.value,
                    onValueChange = {
                        isGPAValid.value = checkBigDecimal(it)
                        universityInfo.gpa.value = it
                    },
                    label = { Text("GPA") },
                    singleLine = true,
                    modifier = Modifier.weight(1f),
                    isError = !isGPAValid.value,
                    errorMessage = { Text("GPA should be a float number") }
                )
            }
        }
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
        singleLine = true,
        modifier = modifier
    )
}