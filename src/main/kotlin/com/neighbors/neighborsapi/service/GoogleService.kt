package com.neighbors.neighborsapi.service

import com.google.api.client.googleapis.auth.oauth2.GoogleIdToken
import com.google.api.client.googleapis.auth.oauth2.GoogleIdTokenVerifier
import com.google.api.client.http.javanet.NetHttpTransport
import com.google.api.client.json.gson.GsonFactory
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GoogleService(
    @Value("\${google.token}") val googleToken: String,
) {
    private val logger: Logger = LoggerFactory.getLogger(GoogleService::class.java)

    fun verifyGoogleToken(token: String): GoogleIdToken {
        val googleIdTokenVerifier =
            GoogleIdTokenVerifier
                .Builder(NetHttpTransport(), GsonFactory())
                .setAudience(listOf(googleToken))
                .build()
        return runCatching {
            googleIdTokenVerifier.verify(token)
        }.onSuccess {
            logger.info("Successfully verify token for user ${it.payload.email}")
        }.onFailure {
            logger.error("Cannot verify token {$token}")
            throw Exception("Cannot verify the token.")
        }.getOrThrow()
    }
}
