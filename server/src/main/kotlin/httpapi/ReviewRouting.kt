package httpapi

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
            if (id == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            val result = backend.getReviews(id)
            call.respond(HttpStatusCode.OK, result)
        }

        post {
            val review = call.receive<Review>()
            backend.postReview(review)
        }
    }
}
