package com.neighbors.neighborsapi.controller

import com.neighbors.neighborsapi.service.UserService
import org.springframework.security.core.annotation.AuthenticationPrincipal
import org.springframework.security.core.userdetails.User
import org.springframework.web.bind.annotation.CrossOrigin
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/v1/users")
@CrossOrigin(origins = ["*"])
class UserController(
    private val userService: UserService,
) {
    @GetMapping("/info")
    fun getUserInfo(
        @AuthenticationPrincipal user: User,
    ) = userService.getUserInfo(user)
}
