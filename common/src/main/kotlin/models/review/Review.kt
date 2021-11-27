package models.review

data class Review(
    val userID: Long,
    val reviewerID: Long,

    val date: String,
    val body: String,
)
