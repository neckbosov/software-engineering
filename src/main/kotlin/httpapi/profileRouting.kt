package httpapi

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.AbstractProfileBackend
import models.profile.InstructorProfile
import models.profile.StudentProfile

fun Route.configureProfileRouting(backend: AbstractProfileBackend) {
    route("/v0/profile") {
        get("/student_profile") {
            val id = call.request.queryParameters["id"]?.toIntOrNull()
            if (id == null) {
                call.respond(status = HttpStatusCode.BadRequest, "invalid id value")
                return@get
            }
            // todo handle exception
            val result = backend.getStudentProfile(id)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/instructor_profile") {
            val id = call.request.queryParameters["id"]?.toIntOrNull()
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
            val id = -1 //TODO("receive id fro session")
            backend.postStudentProfile(id, profile)
            call.respond(HttpStatusCode.OK)
        }

        post("/instructor_profile") {

            // todo handle exception

            val profile = call.receive<InstructorProfile>()
            val id = -1 //TODO("receive id fro session")

            backend.postInstructorProfile(id, profile)
            call.respond(HttpStatusCode.OK)
        }
    }
}
