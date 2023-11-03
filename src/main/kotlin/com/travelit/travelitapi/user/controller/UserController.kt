package com.travelit.travelitapi.user.controller

import com.travelit.travelitapi.user.service.UserService
import com.travelit.travelitapi.database.dto.Token
import com.travelit.travelitapi.database.dto.User
import jakarta.validation.Valid
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
    fun login(@RequestBody @Valid user: User): ResponseEntity<Token> {
        // need to make token logic
        var res = userService.logIn(user)
        val token = Token(accessToken = "accessToken", refreshToken = "refreshToken")
        if (token.accessToken == null) {

        }
        return ResponseEntity.ok(token)
    }

    // sign up

    // follow

    // unfollow

    // profile
}