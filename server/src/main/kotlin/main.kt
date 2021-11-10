import backend.SimpleProfileBackend
import httpapi.configureProfileRouting
import io.ktor.application.*
import io.ktor.server.engine.*
import io.ktor.server.netty.*
import io.ktor.features.*
import io.ktor.http.*
import io.ktor.routing.*
import io.ktor.serialization.*
import kotlinx.serialization.json.Json

fun main() {
    val profileBackend = SimpleProfileBackend()

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
            configureProfileRouting(profileBackend)
        }
    }.start(wait = true)
}