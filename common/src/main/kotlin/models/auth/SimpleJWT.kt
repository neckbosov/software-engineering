package models.auth

import com.auth0.jwt.JWT
import com.auth0.jwt.JWTVerifier
import com.auth0.jwt.algorithms.Algorithm
import kotlinx.serialization.Serializable

@Serializable
data class UserClaims(val id: Long)

class SimpleJwt(secret: String) {
    private val algorithm = Algorithm.HMAC256(secret)
    val verifier: JWTVerifier = JWT.require(algorithm).build()

    fun sign(claims: UserClaims): Jwt = JWT.create().withClaim("id", claims.id).sign(algorithm)

    fun verify(jwt: Jwt): UserClaims {
        println(jwt)
        val decoded = verifier.verify(jwt)
        return UserClaims(id=decoded.getClaim("id").asLong())
    }

    companion object {
        fun parse(jwt: Jwt): UserClaims {
            val decoded = JWT.decode(jwt)
            return UserClaims(id=decoded.getClaim("id").asLong())
        }
    }
}

typealias Jwt = String