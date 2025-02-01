package com.neighbors.neighborsapi.service

import com.neighbors.neighborsapi.model.User
import com.neighbors.neighborsapi.repository.UserRepository
import org.springframework.stereotype.Service
import kotlin.jvm.optionals.getOrElse

@Service
class UserService(
    private val googleService: GoogleService,
    private val userRepository: UserRepository,
) {
    fun findUserByUsername(email: String) =
        userRepository
            .findByEmail(email)
            .getOrElse { throw Exception("User $email not found") }

    fun checkUserToken(token: String): User {
        val payload = googleService.verifyGoogleToken(token).payload
        return userRepository
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
