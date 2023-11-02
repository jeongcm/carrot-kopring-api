package com.travelit.travelitapi.auth.controller

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
class AuthController {
    @PostMapping("/login")
    fun login(@RequestBody @Valid user: User): ResponseEntity<Token> {
        // need to make token logic
        val token = Token(accessToken = "accessToken", refreshToken = "refreshToken")
        if (token.accessToken == null) {

        }
        return ResponseEntity.ok(token)
    }
}