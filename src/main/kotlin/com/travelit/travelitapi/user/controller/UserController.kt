package com.travelit.travelitapi.user.controller

import com.travelit.travelitapi.common.LogInResponse
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
        return try {
            val token: Token = userService.logIn(user)

            ResponseEntity.ok(LogInResponse(user.userName, token))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error")
        }
    }

    // sign up

    // follow

    // unfollow

    // profile
}