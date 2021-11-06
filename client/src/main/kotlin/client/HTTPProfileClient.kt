package client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import models.AbstractProfileClient
import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile

fun createClient(): HttpClient {
    return HttpClient(CIO) {

    }
}

class HTTPProfileClient(
    private val client: HttpClient,
    private val address: String = "http://0.0.0.0:8080/v0/profile"
) : AbstractProfileClient {
    override suspend fun updateStudentProfile(id: Long, profile: StudentProfile) {
        val response: HttpResponse = client.post("$address/student_profile") {
            body = profile
        }
    }

    override suspend fun getStudentProfile(id: Long): StudentProfile {
        return client.get("$address/student_profile") {
            parameter("id", id)
        }
    }

    override suspend fun updateInstructorProfile(id: Long, profile: InstructorProfile) {
        val response: HttpResponse = client.post("$address/instructor_profile") {
            body = profile
        }
    }

    override suspend fun getInstructorProfile(id: Long): InstructorProfile {
        return client.get("$address/instructor_profile") {
            parameter("id", id)
        }
    }

    override suspend fun getIdByEmail(email: String): Long {
        return client.get("$address/email_by_id") {
            parameter("email", email)
        }
    }

    override suspend fun getProfile(id: Long): UserProfile {
        return client.get(address) {
            parameter("id", id)
        }
    }
}