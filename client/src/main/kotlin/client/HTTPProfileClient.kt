package client

import api.*
import io.ktor.client.*
import io.ktor.client.engine.cio.*
import io.ktor.client.features.json.*
import io.ktor.client.features.json.serializer.*
import io.ktor.client.request.*
import io.ktor.client.statement.*
import io.ktor.http.*
import models.ProfileType
import models.Tag
import models.Tags
import models.auth.EmailPasswordCredentials
import models.auth.GoogleAuthStep
import models.auth.Jwt
import models.chat.Chat
import models.chat.Message
import models.profile.InstructorProfile
import models.profile.StudentProfile
import models.profile.UserProfile
import models.review.Review

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
) : AbstractProfileAPI, AbstractAuthenticationAPI, AbstractSearchAPI, AbstractChatAPI, AbstractReviewAPI {
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
            contentType(ContentType.Application.Json)
            body = prefix
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

    override suspend fun addChat(userId1: Long, userId2: Long): Chat {
        return client.post("$endpoint/chats/chat") {
            contentType(ContentType.Application.Json)
            provideAuth()
            body = Chat(userId1, userId2)
        }
    }

    override suspend fun addMessage(chatId: Long, senderId: Long, content: String): Message {
        return client.post("$endpoint/chats/msg") {
            contentType(ContentType.Application.Json)
            provideAuth()
            body = Message(chatId, senderId, content)
        }
    }

    override suspend fun getChatById(chatId: Long): Chat {
        return client.get("$endpoint/chats/chat?id=$chatId") {
            provideAuth()
        }
    }

    override suspend fun getChatsByUserId(userId: Long): List<Chat> {
        return client.get("$endpoint/chats/user_chats?profileId=$userId") {
            provideAuth()
        }
    }

    override suspend fun getMessageById(messageId: Long): Message {
        return client.get("$endpoint/chats/msg?id=$messageId") {
            provideAuth()
        }
    }

    override suspend fun getMessages(chatId: Long, startPos: Long, endPos: Long): List<Message> {
        return client.get("$endpoint/chats/messages?chatId=$chatId&startPos=$startPos&endPos=$endPos") {
            provideAuth()
        }
    }

    override suspend fun getReviews(userId: Long): List<Review> {
        return client.get("$endpoint/profile/review?id=${userId}") {
            provideAuth()
        }
    }

    override suspend fun postReview(review: Review) {
        return client.post("$endpoint/profile/review") {
            provideAuth()
            body = review
        }
    }
}
