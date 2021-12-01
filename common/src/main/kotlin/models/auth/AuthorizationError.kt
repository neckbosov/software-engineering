package models.auth

open class AuthorizationError(msg: String): Exception(msg)

object AccountIsBannedError: AuthorizationError("your account is suspended")
object NotAuthorizedError: AuthorizationError("not authorized")