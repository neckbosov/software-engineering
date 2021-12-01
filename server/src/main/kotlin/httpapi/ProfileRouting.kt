package httpapi

import backend.api.authaccess.AuthorizedProfileAPI
import error.InvalidBodyError
import error.InvalidQueryParameterError
import error.NotFoundError
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.AbstractProfileAPI
import models.auth.SimpleJwt
import models.profile.InstructorProfile
import models.profile.StudentProfile

fun Route.configureProfileRouting(backend: AuthorizedProfileAPI, jwt: SimpleJwt) {
    route("/v0/profile") {

        get("/") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            authorized(jwt) { agent ->
                val result = try {
                    backend.getProfile(agent.id, id)
                } catch (t: Throwable) {
                    throw NotFoundError("No profile with id $id")
                }
                call.respond(HttpStatusCode.OK, result)
            }
        }

        get("/id_by_email") {
            val email = call.request.queryParameters["email"]
                ?: throw InvalidQueryParameterError("Invalid query parameter `email`")
            authorized(jwt) { agent ->
                val result = try {
                    backend.getIdByEmail(agent.id, email)
                } catch (t: Throwable) {
                    throw NotFoundError("No profile with email $email")
                }
                call.respond(HttpStatusCode.OK, result)
            }
        }

        get("/student_profile") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            authorized(jwt) { agent ->
                val result = try {
                    backend.getStudentProfile(agent.id, id)
                } catch (t: Throwable) {
                    throw NotFoundError("No student profile with id $id")
                }
                call.respond(HttpStatusCode.OK, result)
            }
        }

        get("/instructor_profile") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")
            authorized(jwt) { agent ->
                val result = try {
                    backend.getInstructorProfile(agent.id, id)
                } catch (t: Throwable) {
                    throw NotFoundError("No instructor profile with id $id")
                }
                call.respond(HttpStatusCode.OK, result)
            }
        }

        post("/student_profile") {
            val profile = try {
                call.receive<StudentProfile>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }
            authorized(jwt) { agent ->
                val id = call.request.queryParameters["id"]?.toLongOrNull()
                    ?: agent.id
                backend.updateStudentProfile(agent.id, id, profile)
                call.respond(HttpStatusCode.OK)
            }
        }

        post("/instructor_profile") {
            val profile = try {
                call.receive<InstructorProfile>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }
            authorized(jwt) { agent ->
                val id = call.request.queryParameters["id"]?.toLongOrNull()
                    ?: agent.id
                backend.updateInstructorProfile(agent.id, id, profile)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
