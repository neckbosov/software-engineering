package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import models.profile.StudentProfile


class TMPStudentProfileEdit(
    studentProfile: StudentProfile
): UserProfileInfoEdit(studentProfile) {
    val universityDescription = UniversityDescriptionEdit(studentProfile.universityDescription)
    val cvURL = mutableStateOf(studentProfile.cvURL ?: "")

    fun toStudentProfile(): StudentProfile =
        StudentProfile(
            email = email,
            name = name.value,
            surname = surname.value,
            patronymic = patronymic.value,
            avatarURL = avatarURL.value,
            career = career.map { it.toJobDescription() },
            achievements = achievements.map { it.toAchievementDescription() },
            interestsTags = interestsTags.map { it },
            status = status.value,
            universityDescription = universityDescription.toUniversityDescription(),
            cvURL = cvURL.value.ifEmpty { null },
            reviews = reviews
        )
}