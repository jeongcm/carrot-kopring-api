package com.travelit.travelitapi.user.controller

import com.travelit.travelitapi.user.service.UserService
import com.travelit.travelitapi.database.dto.Token
import com.travelit.travelitapi.database.dto.User
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class UserController(var userService: UserService) {
    // login
    @PostMapping("/login")
    fun login(@RequestBody @Valid user: User): ResponseEntity<Any> {
        // find user

        // 1. find user
        val foundUser: User = userService.findUser(user) ?: return ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found user")

        // need to make token logic
        var token: Token? = userService.logIn(foundUser)
        if (token == null || (token.accessToken == "" || token.refreshToken == "")) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("failed to create token")
        }

        return ResponseEntity.ok(token)
    }

    // sign up

    // follow

    // unfollow

    // profile
}