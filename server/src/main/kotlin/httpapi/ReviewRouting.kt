package httpapi

import error.InvalidBodyError
import error.InvalidQueryParameterError
import error.NotFoundError
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.AbstractReviewAPI
import models.auth.SimpleJwt
import models.review.Review

fun Route.configureReviewRouting(backend: AbstractReviewAPI, jwt: SimpleJwt) {
    route("/v0/profile/review") {
        get {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            val result = try {
                backend.getReviews(id)
            } catch (t: Throwable){
                throw NotFoundError("No user found with id $id")
            }

            call.respond(HttpStatusCode.OK, result)
        }

        post {
            val review = try {
                call.receive<Review>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }
            // TODO check auth!!!!!!
            backend.postReview(review)
        }
    }
}
