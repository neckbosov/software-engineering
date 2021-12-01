package httpapi

import backend.api.SimpleSearchAPI
import backend.api.authaccess.AuthorizedSearchAPI
import error.InvalidBodyError
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.Tags
import models.auth.SimpleJwt

fun Route.configureSearchRouting(backend: AuthorizedSearchAPI, jwt: SimpleJwt) {
    route("/v0") {
        get("/search/students") {
            val tags = try {
                call.receive<Tags>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            authorized(jwt) { agent ->
                val result = backend.searchStudentsByTags(agent.id, tags)
                call.respond(HttpStatusCode.OK, result)
            }
        }

        get("/search/instructors") {
            val tags = try {
                call.receive<Tags>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            authorized(jwt) { agent ->
                val result = backend.searchInstructorsByTags(agent.id, tags)
                call.respond(HttpStatusCode.OK, result)
            }
        }
        get("/search/tags") {
            val prefix = try {
                call.receive<String>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            authorized(jwt) { agent ->
                val result = backend.getTagsByPrefix(agent.id, prefix)
                call.respond(HttpStatusCode.OK, result)
            }
        }
    }
}
