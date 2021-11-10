package httpapi

import io.ktor.application.*
import io.ktor.http.*
import io.ktor.request.*
import io.ktor.response.*
import io.ktor.routing.*
import models.AbstractBackend
import models.Tags
import models.profile.InstructorProfile
import models.profile.StudentProfile

fun Route.configureSearchRouting(backend: AbstractBackend) {
    route("/v0") {

        get("/search/students") {

            val tags = call.receive<Tags>()

            val result = backend.searchStudentsByTags(tags)

            call.respond(HttpStatusCode.OK, result)
        }

        get("/search/instructors") {

            val tags = call.receive<Tags>()

            val result = backend.searchInstructorsByTags(tags)

            call.respond(HttpStatusCode.OK, result)
        }

    }
}
