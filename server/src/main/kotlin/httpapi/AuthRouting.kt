package httpapi

import api.AbstractAuthenticationAPI
import io.ktor.application.*
import io.ktor.http.*
import io.ktor.response.*
import io.ktor.routing.*
import models.ProfileType

fun Route.configureAuthRouting(backend: AbstractAuthenticationAPI) {
    route("/v0/auth") {

        get("/register/emailpassword") {
            error("not implemented yeet")
        }

        get("/login/emailpassword") {
            error("not implemented yeet")
        }

        get("/register/google") {
            val profileType =
                ProfileType.valueOf(call.request.queryParameters["profile_type"] ?: error("invalid profile type"))
            val result = backend.registerViaGoogle(profileType)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/register/google/post") {
            val token = call.request.queryParameters["token"] ?: error("no token provided")
            val result = backend.postRegisterViaGoogle(token)
            call.respond(HttpStatusCode.OK, result)
        }

        get("/login/google") {
            val result = backend.loginViaGoogle()
            call.respond(HttpStatusCode.OK, result)
        }

        get("/login/google/post") {
            val token = call.request.queryParameters["token"] ?: error("no token provided")
            val result = backend.postLoginViaGoogle(token)
            call.respond(HttpStatusCode.OK, result)
        }
    }
}
