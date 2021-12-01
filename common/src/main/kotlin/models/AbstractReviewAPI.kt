package models

import models.review.Review

interface AbstractReviewAPI {
    suspend fun getReviews(userId: Long): List<Review>
    suspend fun postReview(review: Review)
}