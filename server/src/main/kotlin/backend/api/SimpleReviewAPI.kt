package backend.api

import api.AbstractReviewAPI
import db.SimpleDatabase
import db.dao.*
import models.review.Review
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.transaction

class SimpleReviewAPI(val database: SimpleDatabase) : AbstractReviewAPI {
    init {
        transaction {
            SchemaUtils.create(Reviews)
        }
    }

    override suspend fun getReviews(userId: Long): List<Review> {
        return database.getReviews(userId)
    }

    override suspend fun postReview(review: Review) {
        return database.postReview(review)
    }
}