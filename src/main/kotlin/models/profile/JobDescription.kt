package models.profile

import kotlinx.serialization.Serializable

@Serializable
data class JobDescription(
    val place: String,
    val position: String,
    val period: Pair<String, String>, // TODO
)
