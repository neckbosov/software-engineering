package ui.profile.edit.models

import androidx.compose.runtime.mutableStateOf
import models.profile.AchievementDescription

class AchievementDescriptionEdit(
    achievementDescription: AchievementDescription
) {
    val type = mutableStateOf(achievementDescription.type)
    val title = mutableStateOf(achievementDescription.title)
    val description = mutableStateOf(achievementDescription.description)
    val date = mutableStateOf(achievementDescription.date)

    fun toAchievementDescription(): AchievementDescription =
        AchievementDescription(
            type = type.value,
            title = title.value,
            description = description.value,
            date = date.value
        )
}