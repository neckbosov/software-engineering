package httpapi

import backend.SimpleSearchAPI
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

            val tags = call.receive<Tags>()
            authorized(jwt) {
                val result = backend.searchStudentsByTags(tags)

                call.respond(HttpStatusCode.OK, result)
            }
        }

        get("/search/instructors") {

            val tags = call.receive<Tags>()
            authorized(jwt) {
                val result = backend.searchInstructorsByTags(tags)

                call.respond(HttpStatusCode.OK, result)
            }
        }
        post("/search/tags") {
            val prefix = call.receive<String>()
            authorized(jwt) {
                val result = backend.getTagsByPrefix(prefix)
                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}
