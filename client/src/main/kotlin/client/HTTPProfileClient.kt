package client

import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import models.*
import models.auth.EmailPasswordCredentials
import models.auth.GoogleAuthStep
import models.auth.Jwt
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
    private val endpoint: String,
    private val currentJwt: () -> Jwt?
) : AbstractProfileAPI, AbstractAuthenticationAPI, AbstractSearchAPI {

    private fun HttpRequestBuilder.provideAuth() {
        val jwt = currentJwt()
        if (jwt != null) {
            header("Authorization", "Bearer $jwt")
        }
    }

    override suspend fun updateStudentProfile(id: Long, profile: StudentProfile) {
        val response: HttpResponse = client.post("$endpoint/profile/student_profile") {
            provideAuth()
            contentType(ContentType.Application.Json)
            body = profile
        }
    }

    override suspend fun getStudentProfile(id: Long): StudentProfile {
        return client.get("$endpoint/profile/student_profile") {
            provideAuth()
            parameter("id", id)
        }
    }

    override suspend fun updateInstructorProfile(id: Long, profile: InstructorProfile) {
        val response: HttpResponse = client.post("$endpoint/profile/instructor_profile") {
            contentType(ContentType.Application.Json)
            provideAuth()
            body = profile
        }
    }

    override suspend fun getInstructorProfile(id: Long): InstructorProfile {
        return client.get("$endpoint/profile/instructor_profile") {
            provideAuth()
            parameter("id", id)
        }
    }

    override suspend fun getIdByEmail(email: String): Long {
        return client.get("$endpoint/profile/id_by_email") {
            provideAuth()
            parameter("email", email)
        }
    }

    override suspend fun getProfile(id: Long): UserProfile {
        return client.get("$endpoint/profile/") {
            provideAuth()
            parameter("id", id)
        }
    }

    private val searchAddress = "$endpoint/search"

    override suspend fun searchStudentsByTags(tags: Tags): List<StudentProfile> {
        return client.get("$searchAddress/students") {
            provideAuth()
            contentType(ContentType.Application.Json)
            body = tags
        }
    }

    override suspend fun searchInstructorsByTags(tags: Tags): List<InstructorProfile> {
        return client.get("$searchAddress/instructors") {
            provideAuth()
            contentType(ContentType.Application.Json)
            body = tags
        }
    }

    override suspend fun getTagsByPrefix(prefix: String): List<Tag> {
        return client.get("$searchAddress/tags") {
            provideAuth()
            parameter("prefix", prefix)
        }
    }


    override suspend fun registerViaEmailPassword(creds: EmailPasswordCredentials, profileType: ProfileType): Jwt {
        error("not implemented yeet")
    }

    override suspend fun loginViaEmailPassword(creds: EmailPasswordCredentials): Jwt {
        error("not implemented yeet")
    }

    override suspend fun registerViaGoogle(profileType: ProfileType): GoogleAuthStep {
        return client.get("$endpoint/auth/register/google") {
            parameter("profile_type", profileType.toString())
        }
    }

    override suspend fun postRegisterViaGoogle(token: String): Jwt {
        return client.get("$endpoint/auth/register/google/post") {
            parameter("token", token)
        }
    }

    override suspend fun loginViaGoogle(): GoogleAuthStep {
        return client.get("$endpoint/auth/login/google")
    }

    override suspend fun postLoginViaGoogle(token: String): Jwt {
        return client.get("$endpoint/auth/login/google/post") {
            parameter("token", token)
        }
    }
}
