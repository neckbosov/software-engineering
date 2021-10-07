package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.toMutableStateList
import androidx.compose.ui.text.input.TextFieldValue
import models.profile.UserProfile

sealed class UserProfileInfoEdit(
    profile: UserProfile
) {
    val name = mutableStateOf(TextFieldValue(profile.name))
    val surname = mutableStateOf(TextFieldValue(profile.surname))
    val patronymic = mutableStateOf(TextFieldValue(profile.patronymic))
    val avatarURL = mutableStateOf(TextFieldValue(profile.avatarURL))
    val career = profile.career.map { JobDescriptionEdit(it) }.toMutableStateList()
    val achievements = profile.achievements.map { AchievementDescriptionEdit(it) }.toMutableStateList()
    val interestsTags = profile.interestsTags.map { TextFieldValue(it) }.toMutableStateList()
    val status = mutableStateOf(profile.status)
}