package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import models.profile.StudentProfile


class TMPStudentProfileEdit(
    studentProfile: StudentProfile
): UserProfileInfoEdit(studentProfile) {
    val universityDescription = UniversityDescriptionEdit(studentProfile.universityDescription)
    val cvURL = mutableStateOf(TextFieldValue(studentProfile.cvURL ?: ""))

    fun toStudentProfile(): StudentProfile =
        StudentProfile(
            name = name.value.text,
            surname = surname.value.text,
            patronymic = patronymic.value.text,
            avatarURL = avatarURL.value.text,
            career = career.map { it.toJobDescription() },
            achievements = achievements.map { it.toAchievementDescription() },
            interestsTags = interestsTags.map { it.text },
            status = status.value,
            universityDescription = universityDescription.toUniversityDescription(),
            cvURL = cvURL.value.text.ifEmpty { null }
        )
}