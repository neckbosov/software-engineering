package backend

import db.SimpleDatabase
import models.AbstractReviewAPI
import models.review.Review

class SimpleReviewAPI(val database: SimpleDatabase) : AbstractReviewAPI {
    override suspend fun getReviews(userId: Long): List<Review> {
        return database.getReviews(userId)
    }

    override suspend fun postReview(review: Review) {
        return database.postReview(review)
    }
}