package models.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.serialization.Serializable

@Serializable
data class UserClaims(val id: Long)

class SimpleJwt(val secret: String) {
    private val algorithm = Algorithm.HMAC256(secret)
    val verifier = JWT.require(algorithm).build()

    fun sign(claims: UserClaims): Jwt = JWT.create().withClaim("id", claims.id).sign(algorithm)

    fun decode(jwt: Jwt): UserClaims {
        val decoded = verifier.verify(jwt)
        return UserClaims(id=decoded.getClaim("id").asLong())
    }
}

typealias Jwt = String