package com.neighbors.neighborsapi.service

import com.neighbors.neighborsapi.repository.UserRepository
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.security.core.userdetails.User
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
) {
    private val logger: Logger = LoggerFactory.getLogger(UserService::class.java)

    fun getUserInfo(user: User) =
        userRepository
            .findByEmail(user.username)
            .orElseThrow { Exception("User with email: ${user.username} not found") }
}
