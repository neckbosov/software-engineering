package httpapi

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.AbstractBackend
import models.profile.InstructorProfile
import models.profile.StudentProfile

fun Route.configureProfileRouting(backend: AbstractBackend) {
    route("/v0/profile") {
        get("/student_profile") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            // todo handle exception
            val result = backend.getStudentProfile(id)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/instructor_profile") {
            val id = call.request.queryParameters["id"]?.toLongOrNull()
            if (id == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            // todo handle exception
            val result = backend.getInstructorProfile(id)
            call.respond(HttpStatusCode.OK, result)
        }

        post("/student_profile") {
            // todo handle exception

            val profile = call.receive<StudentProfile>()
            val id = -1L //TODO("receive id fro session")
            backend.updateStudentProfile(id, profile)
            call.respond(HttpStatusCode.OK)
        }

        post("/instructor_profile") {

            // todo handle exception

            val profile = call.receive<InstructorProfile>()
            val id = -1L //TODO("receive id fro session")

            backend.updateInstructorProfile(id, profile)
            call.respond(HttpStatusCode.OK)
        }
    }
}
