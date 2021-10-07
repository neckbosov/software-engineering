package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.text.input.TextFieldValue
import models.profile.AchievementDescription

class AchievementDescriptionEdit(
    achievementDescription: AchievementDescription
) {
    val type = mutableStateOf(TextFieldValue(achievementDescription.type))
    val title = mutableStateOf(TextFieldValue(achievementDescription.title))
    val description = mutableStateOf(TextFieldValue(achievementDescription.description))
    val date = mutableStateOf(TextFieldValue(achievementDescription.date))

    fun toAchievementDescription(): AchievementDescription =
        AchievementDescription(
            type = type.value.text,
            title = title.value.text,
            description = description.value.text,
            date = date.value.text
        )
}