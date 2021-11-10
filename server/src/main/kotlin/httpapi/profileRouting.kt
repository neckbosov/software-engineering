package httpapi

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.Json.Default.encodeToString
import models.AbstractProfileBackend
import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile

fun Route.configureProfileRouting(backend: AbstractProfileBackend) {
    route("/v0") {

        get("/profile") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            // todo handle exception
            val result = backend.getProfile(id)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/profile/id_by_email") {
            val email = call.request.queryParameters["email"]
            if (email == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            // todo handle exception
            val result = backend.getIdByEmail(email)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/profile/student_profile") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            // todo handle exception
            val result = backend.getStudentProfile(id)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/profile/instructor_profile") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            // todo handle exception
            val result = backend.getInstructorProfile(id)
            call.respond(HttpStatusCode.OK, result)
        }

        post("/profile/student_profile") {
            // todo handle exception

            val profile = call.receive<StudentProfile>()
            val id = -1L //TODO("receive id fro session")
            backend.updateStudentProfile(id, profile)
            call.respond(HttpStatusCode.OK)
        }

        post("/profile/instructor_profile") {

            // todo handle exception

            val profile = call.receive<InstructorProfile>()
            val id = -1L //TODO("receive id fro session")

            backend.updateInstructorProfile(id, profile)
            call.respond(HttpStatusCode.OK)
        }
    }
}
