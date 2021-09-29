package models.profile

import kotlinx.serialization.Serializable

@Serializable
data class UniversityDescription(
    val universityName: String,
    val faculty: String,
    val grade: String,
    val period: Pair<String, String>, // TODO
    val gpa: Float,
)
