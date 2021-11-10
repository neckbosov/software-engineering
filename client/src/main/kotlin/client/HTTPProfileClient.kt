package client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import models.AbstractProfileClient
import models.Tags
import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile

fun createClient(): HttpClient {
    return HttpClient(CIO) {
        install(JsonFeature) {
            serializer = KotlinxSerializer(
                json = kotlinx.serialization.json.Json {
                    useArrayPolymorphism = true
                    //ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                }
            )
        }
    }
}

class HTTPProfileClient(
    private val client: HttpClient,
    private val address: String = "http://0.0.0.0:8080/v0"
) : AbstractProfileClient {

    private val profileAddress = "$address/profile"

    override suspend fun updateStudentProfile(id: Long, profile: StudentProfile) {
        val response: HttpResponse = client.post("$profileAddress/student_profile") {
            contentType(ContentType.Application.Json)
            body = profile
        }
    }

    override suspend fun getStudentProfile(id: Long): StudentProfile {
        return client.get("$profileAddress/student_profile") {
            parameter("id", id)
        }
    }

    override suspend fun updateInstructorProfile(id: Long, profile: InstructorProfile) {
        val response: HttpResponse = client.post("$profileAddress/instructor_profile") {
            contentType(ContentType.Application.Json)
            body = profile
        }
    }

    override suspend fun getInstructorProfile(id: Long): InstructorProfile {
        return client.get("$profileAddress/instructor_profile") {
            parameter("id", id)
        }
    }

    override suspend fun getIdByEmail(email: String): Long {
        return client.get("$profileAddress/id_by_email") {
            parameter("email", email)
        }
    }

    override suspend fun getProfile(id: Long): UserProfile {
        return client.get(profileAddress) {
            parameter("id", id)
        }
    }

    private val searchAddress = "$address/search"

    override suspend fun searchStudentsByTags(tags: Tags): List<StudentProfile> {
        return client.get("$searchAddress/students") {
            contentType(ContentType.Application.Json)
            body = tags
        }
    }

    override suspend fun searchInstructorsByTags(tags: Tags): List<InstructorProfile> {
        return client.get("$searchAddress/instructors") {
            contentType(ContentType.Application.Json)
            body = tags
        }
    }

}
