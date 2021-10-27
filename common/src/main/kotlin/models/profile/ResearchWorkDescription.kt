package models.profile

import kotlinx.serialization.Serializable

@Serializable
data class ResearchWorkDescription(
    val name: String,
    val description: String,
    val detailsURL: String,
)
