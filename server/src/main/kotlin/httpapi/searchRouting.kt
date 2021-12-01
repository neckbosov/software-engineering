package httpapi

import backend.api.SimpleSearchAPI
import error.InvalidBodyError
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.Tags
import models.auth.SimpleJwt

fun Route.configureSearchRouting(backend: SimpleSearchAPI, jwt: SimpleJwt) {
    route("/v0") {

        get("/search/students") {

            val tags = try {
                call.receive<Tags>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            authorized(jwt) {

                val result = backend.searchStudentsByTags(tags)
                call.respond(HttpStatusCode.OK, result)
            }
        }

        get("/search/instructors") {

            val tags = try {
                call.receive<Tags>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            authorized(jwt) {
                val result = backend.searchInstructorsByTags(tags)

                call.respond(HttpStatusCode.OK, result)
            }
        }
        get("/search/tags") {

            val prefix = try {
                call.receive<String>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            authorized(jwt) {
                val result = backend.getTagsByPrefix(prefix)

                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}
