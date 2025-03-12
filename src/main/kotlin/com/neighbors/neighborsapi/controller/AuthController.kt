package com.neighbors.neighborsapi.controller

import com.neighbors.neighborsapi.jwt.JwtService
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AuthController(
    private val jwtService: JwtService,
) {
    @PostMapping("/google")
    fun googleLogin(
        @RequestBody request: GoogleAuthRequest,
    ): ResponseEntity<AuthResponse> =
        jwtService
            .authorizeByGoogle(request)
            .let { ResponseEntity.ok(it) }

    @PostMapping("/logout")
    fun googleLogout(
        @RequestHeader("Authorization") bearer: String,
    ): ResponseEntity<Void> =
        jwtService
            .googleLogout(bearer)
            .let { ResponseEntity.ok().build() }
}

data class GoogleAuthRequest(
    val idToken: String,
)

data class AuthResponse(
    val accessToken: String,
    val user: GoogleUser,
)

data class GoogleUser(
    val id: String,
    val email: String,
    val name: String,
)
