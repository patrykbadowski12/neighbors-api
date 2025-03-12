package com.neighbors.neighborsapi.jwt

import com.auth0.jwt.JWT
import com.auth0.jwt.algorithms.Algorithm
import com.auth0.jwt.interfaces.DecodedJWT
import com.neighbors.neighborsapi.controller.GoogleUser
import org.slf4j.LoggerFactory
import java.util.Date

object JwtUtil {
    private val logger = LoggerFactory.getLogger(JwtUtil::class.java)

    private const val DEFAULT_SECRET_KEY = "superSecretKey"
    private const val EXPIRATION_TIME = 60 * 60 * 1000

    private val SECRET_KEY = System.getenv("JWT_SECRET") ?: DEFAULT_SECRET_KEY

    private val algorithm: Algorithm = Algorithm.HMAC256(SECRET_KEY)

    fun generateJwt(
        user: GoogleUser,
        roles: List<String> = listOf("USER"),
    ): String =
        JWT
            .create()
            .withSubject(user.id)
            .withClaim("email", user.email)
            .withClaim("name", user.name)
            .withClaim("roles", roles)
            .withExpiresAt(Date(System.currentTimeMillis() + EXPIRATION_TIME))
            .sign(algorithm)

    fun validateJwt(token: String): Boolean =
        try {
            val decodedJWT = decodeJwt(token)
            !decodedJWT.expiresAt.before(Date())
        } catch (e: Exception) {
            logger.error("Validation error JWT: ${e.message}")
            false
        }

    fun getTokenExpiration(token: String) = decodeJwt(token).expiresAt.time.minus(System.currentTimeMillis())

    fun getUsername(token: String): String = decodeJwt(token).subject

    fun getEmail(token: String): String = decodeJwt(token).getClaim("email").asString()

    fun getRoles(token: String): List<String> = decodeJwt(token).getClaim("roles").asList(String::class.java) ?: listOf("USER")

    fun getClaims(token: String): Map<String, Any> = decodeJwt(token).claims.mapValues { it.value }

    private fun decodeJwt(token: String): DecodedJWT = JWT.require(algorithm).build().verify(token)
}
