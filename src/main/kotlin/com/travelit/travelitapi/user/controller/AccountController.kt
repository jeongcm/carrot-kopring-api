package com.travelit.travelitapi.user.controller

import com.travelit.travelitapi.common.LogInResponse
import com.travelit.travelitapi.user.service.AccountService
import com.travelit.travelitapi.database.dto.Token
import com.travelit.travelitapi.database.dto.Account
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/auth")
class AccountController(var userService: AccountService) {
    // test

    @GetMapping("/")
    fun getAccount(): ResponseEntity<Any> {
        return ResponseEntity.ok("ok")
    }

    // login
    @PostMapping("/logIn")
    fun login(@RequestBody @Valid account: Account): ResponseEntity<Any> {
        return try {
            val token: Token = userService.logIn(account)

            ResponseEntity.ok(LogInResponse(account.name, token))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error:" + e.message)
        }
    }

    // sign up
    @PostMapping("/signUp")
    fun signUp(@RequestBody @Valid account: Account): ResponseEntity<Any> {
        return try {
            userService.signUp(account)

            ResponseEntity.ok(account)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request")
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error:" + e.message)
        }
    }
    // follow

    // unfollow

    // profile
}