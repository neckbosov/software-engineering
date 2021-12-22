package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import models.profile.UserProfile

sealed class UserProfileInfoEdit(
    profile: UserProfile
) {
    val email = profile.email
    val name = mutableStateOf(profile.name)
    val surname = mutableStateOf(profile.surname)
    val patronymic = mutableStateOf(profile.patronymic ?: "")
    val avatarURL = mutableStateOf(profile.avatarURL ?: "")
    val career = profile.career.map { JobDescriptionEdit(it) }.toMutableStateList()
    val achievements = profile.achievements.map { AchievementDescriptionEdit(it) }.toMutableStateList()
    val interestsTags = profile.interestsTags.map { it }.toMutableStateList()
    val status = mutableStateOf(profile.status)
    val validToSubmit = mutableStateOf(true)
}