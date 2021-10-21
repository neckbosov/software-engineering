package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import models.profile.InstructorProfile

class TMPInstructorProfileEdit(
    instructorProfile: InstructorProfile
): UserProfileInfoEdit(instructorProfile) {
    val degree = mutableStateOf(instructorProfile.degree)
    val works = instructorProfile.works.map { ResearchWorkDescriptionEdit(it) }.toMutableStateList()

    fun toInstructorProfile(): InstructorProfile =
        InstructorProfile(
            email = email,
            name = name.value,
            surname = surname.value,
            patronymic = patronymic.value,
            avatarURL = avatarURL.value,
            career = career.map { it.toJobDescription() },
            achievements = achievements.map { it.toAchievementDescription() },
            interestsTags = interestsTags.map { it },
            status = status.value,
            degree = degree.value,
            works = works.map { it.toResearchWorkDescription() }
        )
}