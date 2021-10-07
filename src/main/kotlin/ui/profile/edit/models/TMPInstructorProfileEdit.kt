package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import models.profile.InstructorProfile

class TMPInstructorProfileEdit(
    instructorProfile: InstructorProfile
): UserProfileInfoEdit(instructorProfile) {
    val degree = mutableStateOf(TextFieldValue(instructorProfile.degree))
    val works = instructorProfile.works.map { ResearchWorkDescriptionEdit(it) }.toMutableStateList()

    fun toInstructorProfile(): InstructorProfile =
        InstructorProfile(
            name = name.value.text,
            surname = surname.value.text,
            patronymic = patronymic.value.text,
            avatarURL = avatarURL.value.text,
            career = career.map { it.toJobDescription() },
            achievements = achievements.map { it.toAchievementDescription() },
            interestsTags = interestsTags.map { it.text },
            status = status.value,
            degree = degree.value.text,
            works = works.map { it.toResearchWorkDescription() }
        )
}