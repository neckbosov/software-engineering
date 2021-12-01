package httpapi

import error.*
import io.ktor.application.*
import io.ktor.features.StatusPages
import io.ktor.http.*
import io.ktor.response.*

fun StatusPages.Configuration.setup(){
    exception<AuthException> { cause ->
        call.respond(HttpStatusCode.BadRequest, cause.message ?: "Auth error")
    }

    exception<NotAuthorisedError> { cause ->
        call.respond(HttpStatusCode.Unauthorized, cause.message ?: "Not authorized error")
    }

    exception<NotFoundError> { cause ->
        call.respond(HttpStatusCode.NotFound, cause.message ?: "Auth error")
    }

    exception<InvalidQueryParameterError> { cause ->
        call.respond(HttpStatusCode.UnprocessableEntity,cause.message ?: "Invalid query parameter")
    }

    exception<InvalidBodyError> { cause ->
        call.respond(HttpStatusCode.UnprocessableEntity,cause.message ?: "Invalid body")
    }

    exception<Exception> {
        call.respond(HttpStatusCode.InternalServerError)
    }
}
