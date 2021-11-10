package auth

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import kotlinx.serialization.decodeFromString
import kotlinx.serialization.json.Json
import org.http4k.client.JavaHttpClient
import org.http4k.core.Method
import org.http4k.core.Request
import org.http4k.core.Response
import org.http4k.core.Status
import org.http4k.routing.bind
import org.http4k.routing.routes
import org.http4k.server.Netty
import org.http4k.server.asServer
import java.io.File
import java.net.URL
import java.net.URLEncoder
import java.sql.Timestamp


data class GoogleCredentials(val accessToken: String, val refreshToken: String, val expiresAt: Timestamp)

class GoogleOAuthHandler(
    val appCredentials: GoogleAppCredentials,
    var hook: (state: String?, creds: GoogleCredentials) -> Unit
) {
    val serverPort: Int = 9239

    val callbackHandler = handler@{ request: Request ->
        val authCode = request.query("code")
            ?: return@handler Response(Status.BAD_REQUEST).body("Something went wrong: " + request.query("error"))
        val state = request.query("state")
        return@handler exchangeAuthCodeOnTokens(authCode, state)
    }

    val exchangeAuthCodeOnTokens = f@{ authCode: String, state: String? ->
        val httpClient = JavaHttpClient()
        val tokenRequest = Request(Method.POST, "https://oauth2.googleapis.com/token")
            .header("Content-Type", "application/x-www-form-urlencoded")
            .body(
                listOf(
                    "code" to authCode,
                    "client_id" to appCredentials.clientId,
                    "client_secret" to appCredentials.clientSecret,
                    "redirect_uri" to "http://localhost:$serverPort/callback",
                    "grant_type" to "authorization_code"
                ).joinToString("&") {
                    val value = URLEncoder.encode(it.second, "utf-8")
                    "${it.first}=${value}"
                }
            )

        val tokenResponse = httpClient(tokenRequest)
        if (tokenResponse.status.code != 200) {
            return@f Response(Status.BAD_REQUEST).body("Something went wrong:<br>" + tokenResponse.body)
        }

        val data = Json { ignoreUnknownKeys = true }.decodeFromString<TokensData>(tokenResponse.body.toString())
        hook(
            state,
            GoogleCredentials(
                data.accessToken,
                data.refreshToken,
                Timestamp(System.currentTimeMillis() + data.expiresIn * 1000)
            )
        )

        return@f Response(Status.OK).body(
            """
            <html>
            <body>
                <h2 style="text-align:center">
                Success!<br>You may now close this page and return to the application.
                </h2>
            </body>
            </html>
        """.trimIndent()
        )
    }

    val server = routes(
        "/callback" bind callbackHandler,
    ).asServer(Netty(serverPort)).start()

    fun makeOAuthURI(state: String) = URL(
        "https://accounts.google.com/o/oauth2/v2/auth?" + listOf(
            "client_id=${appCredentials.clientId}",
            "redirect_uri=http://localhost:$serverPort/callback",
            "response_type=code",
            "scope=email%20profile",
            "access_type=offline",
            "state=${state}"
        ).joinToString("&")
    ).toURI()

    @Serializable
    private data class TokensData(
        @SerialName("access_token") val accessToken: String,
        @SerialName("expires_in") val expiresIn: Int,
        @SerialName("refresh_token") val refreshToken: String
    )
}

data class GoogleAppCredentials(val clientId: String, val clientSecret: String) {
    companion object {
        fun fromFile(filename: String): GoogleAppCredentials =
            File(filename).readLines().let { lines -> GoogleAppCredentials(lines[0], lines[1]) }
    }
}