package auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.http4k.client.JavaHttpClient
import org.http4k.core.Method
import org.http4k.core.Request

class GoogleApiException(message: String): Exception(message)

class GoogleApi(val credentials: GoogleCredentials) {
    data class UserInfo(val name: String, val email: String, val appId: String, val avatarUrl: String)

    fun userInfo(): UserInfo {
        val client = JavaHttpClient()
        val req =
            Request(Method.GET, "https://www.googleapis.com/oauth2/v1/userinfo?access_token=${credentials.accessToken}")
        val response = client(req)
        if (response.status.code != 200) {
            throw GoogleApiException("failed to retrieve userinfo: $response")
        }
        val googleUserInfo = Json { ignoreUnknownKeys = true }.decodeFromString<GoogleUserInfo>(response.body.toString())
        return UserInfo(googleUserInfo.name, googleUserInfo.email, googleUserInfo.id, googleUserInfo.avatarUrl)
    }

    @Serializable
    private data class GoogleUserInfo(
        @SerialName("id") val id: String,
        @SerialName("email") val email: String,
        @SerialName("name") val name: String,
        @SerialName("picture") val avatarUrl: String,
    )
}