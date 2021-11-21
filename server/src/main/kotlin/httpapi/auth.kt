package httpapi

import backend.SimpleAuthenticationAPI
import io.ktor.application.*
import io.ktor.util.pipeline.*
import models.auth.Jwt
import models.auth.SimpleJwt
import models.auth.UserClaims

fun PipelineContext<Unit, ApplicationCall>.extractJwt(): Jwt? {
    val header = call.request.headers["Authorization"]
    return header?.let {
        val prefix = "Bearer "
        if (!it.startsWith(prefix)) {
            throw SimpleAuthenticationAPI.AuthException("invalid JWT")
        }
        it.substring(prefix.length)
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.authorized(
    jwtInstance: SimpleJwt,
    block: suspend PipelineContext<Unit, ApplicationCall>.(UserClaims) -> Unit
) {
    val jwt = extractJwt() ?: error("not authorized")
    val claims = jwtInstance.verify(jwt)
    block(claims)
}