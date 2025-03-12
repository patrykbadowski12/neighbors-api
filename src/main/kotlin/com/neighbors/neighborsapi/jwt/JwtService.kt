package com.neighbors.neighborsapi.jwt

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.neighbors.neighborsapi.controller.AuthResponse
import com.neighbors.neighborsapi.controller.GoogleAuthRequest
import com.neighbors.neighborsapi.controller.GoogleUser
import com.neighbors.neighborsapi.model.User
import com.neighbors.neighborsapi.repository.UserRepository
import com.neighbors.neighborsapi.service.GoogleService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.data.redis.core.StringRedisTemplate
import org.springframework.stereotype.Service
import java.util.concurrent.TimeUnit

@Service
class JwtService(
    private val googleService: GoogleService,
    private val userRepository: UserRepository,
    private val redisTemplate: StringRedisTemplate,
) {
    private val logger: Logger = LoggerFactory.getLogger(JwtService::class.java)

    fun authorizeByGoogle(request: GoogleAuthRequest): AuthResponse {
        val payload = googleService.verifyGoogleToken(request.idToken).payload
        val googleUser =
            GoogleUser(
                id = payload.subject,
                email = payload.email,
                name = payload["name"] as String,
            )
        val jwt = JwtUtil.generateJwt(googleUser)
        storeUserDataIfNotFound(payload)
        return AuthResponse(jwt, googleUser)
    }

    fun googleLogout(bearer: String) {
        val token = bearer.substring(7)
        let {
            JwtUtil.getTokenExpiration(token)
        }.takeIf { it > 0 }
            ?.let {
                logger.info("Storing token: $token in redis blacklist.")
                redisTemplate.opsForValue().set(token, "blacklisted", it, TimeUnit.MILLISECONDS)
            } ?: logger.info("Token $token already expired.")
    }

    fun isTokenBlacklisted(token: String): Boolean = redisTemplate.hasKey(token)

    private fun storeUserDataIfNotFound(payload: GoogleIdToken.Payload) {
        userRepository
            .findByEmail(payload.email)
            .orElseGet {
                userRepository.save(
                    User(
                        email = payload.email,
                        name = payload["name"] as String,
                        givenName = payload["given_name"] as String,
                        familyName = payload["family_name"] as String,
                        profilePicture = payload["picture"] as String,
                    ),
                )
            }
    }
}
