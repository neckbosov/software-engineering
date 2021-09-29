package models.profile

import kotlinx.serialization.Serializable

@Serializable
data class AchievementDescription(
    val type: String,
    val title: String,
    val description: String,
    val date: String
)
