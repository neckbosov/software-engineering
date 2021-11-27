package db

import models.Tag
import models.review.Review

interface SimpleDatabase {
    suspend fun getStudentsIDByTag(tags: List<Tag>): List<Long>

    suspend fun getInstructorsIDByTag(tags: List<Tag>): List<Long>
    suspend fun getTagsByPrefix(prefix: String): List<Tag>

    suspend fun getReviews(userId: Long): List<Review>
    suspend fun postReview(review: Review)
}
