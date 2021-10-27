package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import models.profile.UniversityDescription

class UniversityDescriptionEdit(
    universityDescription: UniversityDescription
) {
    val universityName = mutableStateOf(TextFieldValue(universityDescription.universityName))
    val faculty = mutableStateOf(TextFieldValue(universityDescription.faculty))
    val grade = mutableStateOf(TextFieldValue(universityDescription.grade))
    val period1 = mutableStateOf(TextFieldValue(universityDescription.period.first))
    val period2 = mutableStateOf(TextFieldValue(universityDescription.period.second))
    val gpa = mutableStateOf(TextFieldValue(universityDescription.gpa.toString()))
    val course = mutableStateOf(TextFieldValue(universityDescription.course.toString()))

    fun toUniversityDescription(): UniversityDescription = UniversityDescription(
        universityName = universityName.value.text,
        faculty = faculty.value.text,
        grade = grade.value.text,
        period = Pair(period1.value.text, period2.value.text),
        gpa = gpa.value.text.toFloatOrNull() ?: 0.0F,
        course = gpa.value.text.toIntOrNull() ?: 0
    )
}