package httpapi

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

fun Route.configureProfileRouting(backend: AbstractProfileAPI, jwt: SimpleJwt) {
    route("/v0/profile") {

        get("/") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            val result = try {
                backend.getProfile(id)
            } catch (t: Throwable) {
                throw NotFoundError("No profile with id $id")
            }

            call.respond(HttpStatusCode.OK, result)
        }

        get("/id_by_email") {
            val email = call.request.queryParameters["email"]
                ?: throw InvalidQueryParameterError("Invalid query parameter `email`")

            val result = try {
                backend.getIdByEmail(email)
            } catch (t: Throwable) {
                throw NotFoundError("No profile with email $email")
            }
            call.respond(HttpStatusCode.OK, result)
        }

        get("/student_profile") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            val result = try {
                backend.getStudentProfile(id)
            } catch (t: Throwable) {
                throw NotFoundError("No student profile with id $id")
            }
            call.respond(HttpStatusCode.OK, result)
        }

        get("/instructor_profile") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
                ?: throw InvalidQueryParameterError("Invalid query parameter `id`")

            val result = try {
                backend.getInstructorProfile(id)
            } catch (t: Throwable) {
                throw NotFoundError("No instructor profile with id $id")
            }
            call.respond(HttpStatusCode.OK, result)
        }

        post("/student_profile") {

            val profile = try {
                call.receive<StudentProfile>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            authorized(jwt) { claims ->
                val id = claims.id
                backend.updateStudentProfile(id, profile)
                call.respond(HttpStatusCode.OK)
            }
        }

        post("/instructor_profile") {

            val profile = try {
                call.receive<InstructorProfile>()
            } catch (t: Throwable) {
                throw InvalidBodyError("Invalid body")
            }

            authorized(jwt) { claims ->
                val id = claims.id
                backend.updateInstructorProfile(id, profile)
                call.respond(HttpStatusCode.OK)
            }
        }
    }
}
