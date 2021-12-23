package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import models.profile.UniversityDescription

class UniversityDescriptionEdit(
    universityDescription: UniversityDescription
) {
    val universityName = mutableStateOf(universityDescription.universityName)
    val faculty = mutableStateOf(universityDescription.faculty)
    val grade = mutableStateOf(universityDescription.grade)
    val period1 = mutableStateOf(universityDescription.period.first)
    val period2 = mutableStateOf(universityDescription.period.second)
    val gpa = mutableStateOf(universityDescription.gpa.toString())
    val course = mutableStateOf(universityDescription.course.toString())

    fun toUniversityDescription(): UniversityDescription = UniversityDescription(
        universityName = universityName.value,
        faculty = faculty.value,
        grade = grade.value,
        period = Pair(period1.value, period2.value),
        gpa = gpa.value.toFloatOrNull() ?: 0.0F,
        course = course.value.toIntOrNull() ?: 0
    )
}