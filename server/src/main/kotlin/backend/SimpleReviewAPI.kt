package backend

import models.AbstractReviewAPI
import models.review.Review

class SimpleReviewAPI: AbstractReviewAPI {
    override suspend fun getReviews(userId: Long): List<Review> {
        TODO()
    }
    override suspend fun postReview(review: Review) {
        TODO()
    }
}