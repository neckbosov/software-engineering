package models.profile

data class JobDescription(
    val place: String,
    val position: String,
    val period: Pair<String, String>, // TODO
)
