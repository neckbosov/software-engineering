package models.review

import kotlinx.serialization.Serializable

@Serializable
data class Review(
    val userID: Long,
    val reviewerID: Long,

    val date: String,
    val body: String,
)
