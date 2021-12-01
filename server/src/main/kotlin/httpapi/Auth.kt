package httpapi

import error.AuthException
import error.NotAuthorisedError
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
            throw AuthException("invalid JWT")
        }
        it.substring(prefix.length)
    }
}

suspend fun PipelineContext<Unit, ApplicationCall>.authorized(
    jwtInstance: SimpleJwt,
    block: suspend PipelineContext<Unit, ApplicationCall>.(UserClaims) -> Unit
) {
    val jwt = extractJwt() ?: throw NotAuthorisedError("not authorized")
    val claims = jwtInstance.verify(jwt)
    block(claims)
}