import auth.GoogleAppCredentials
import backend.SimpleAuthenticationAPI
import backend.SimpleProfileAPI
import db.SimpleDatabaseImpl
import httpapi.configureAuthRouting
import httpapi.configureProfileRouting
import httpapi.configureSearchRouting
import io.ktor.application.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import kotlinx.serialization.json.Json
import models.auth.SimpleJwt

fun main() {
    val profileBackend = SimpleProfileAPI(SimpleDatabaseImpl)
    val jwtInstance = SimpleJwt("aboba") // TODO: parse secret from some local file
    val authBackend = SimpleAuthenticationAPI(
        jwtInstance,
        GoogleAppCredentials(
            "135814761571-ojbn8ij88grgktaekq12hkmk78h9k3ri.apps.googleusercontent.com",
            "NcZnNeZL8J-7pMXQgkz5zFiX"
        ) // it seems okay to put these secrets here
    )

    embeddedServer(Netty, port = 8080) {
        install(CORS)
        {
            method(HttpMethod.Options)
            header(HttpHeaders.XForwardedProto)
            anyHost()
            host("my-host")
            allowCredentials = true
            allowNonSimpleContentTypes = true
        }
        install(ContentNegotiation) {
            json(
                json = Json {
                    useArrayPolymorphism = true
                    //ignoreUnknownKeys = true
                    isLenient = true
                    encodeDefaults = true
                }
            )
        }
        install(Routing) {
            configureProfileRouting(profileBackend, jwtInstance)
            configureAuthRouting(authBackend)
            configureSearchRouting(profileBackend, jwtInstance)
        }
    }.start(wait = true)
}