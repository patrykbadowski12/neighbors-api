package com.neighbors.neighborsapi.controller

import com.neighbors.neighborsapi.service.UserService
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestHeader
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = ["*"])
class UserController(
    private val userService: UserService,
) {
    @PostMapping("/auth")
    fun auth(
        @RequestHeader("Authorization") token: String,
    ) = userService.checkUserToken(token)

    @GetMapping
    fun getUser(email: String) = userService.findUserByUsername(email)
}
