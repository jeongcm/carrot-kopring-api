package com.travelit.travelitapi.account.controller

import com.travelit.travelitapi.common.LogInResponse
import com.travelit.travelitapi.database.NotFoundEntityException
import com.travelit.travelitapi.account.service.AccountService
import com.travelit.travelitapi.database.dto.Token
import com.travelit.travelitapi.database.dto.Account
import com.travelit.travelitapi.account.repository.AccountRepository
import com.travelit.travelitapi.common.logger.logger
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
class AccountController(var accountService: AccountService) {
    // test
    var logger = logger()
    @GetMapping("/admin")
    fun getAccount(): ResponseEntity<Any> {
//        println(SecurityContextHolder.getContext().authentication.name)
//        println(SecurityContextHolder.getContext().authentication.details)
//        println(SecurityContextHolder.getContext().authentication.authorities)
        logger.info("hello my log")
        logger.error("hello my error log")
        return ResponseEntity.ok("ok")
    }

    // login
    @PostMapping("/login")
    fun login(@RequestBody @Valid account: Account): ResponseEntity<Any> {
        return try {
            val token: Token = accountService.logIn(account)

            ResponseEntity.ok(LogInResponse(account.name, token))
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request error. cause: " + e.message)
        } catch (e: NotFoundEntityException) {
            ResponseEntity.status(HttpStatus.NOT_FOUND).body("not found error. cause: " + e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error. cause: " + e.message)
        }
    }

    // sign up
    @PostMapping("/signUp")
    fun signUp(@RequestBody @Valid account: Account, accountRepository: AccountRepository): ResponseEntity<Any> {
        return try {
            val res = accountService.signUp(account)

            ResponseEntity.ok(res)
        } catch (e: IllegalArgumentException) {
            ResponseEntity.status(HttpStatus.BAD_REQUEST).body("bad request error. cause: " + e.message)
        } catch (e: Exception) {
            ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("internal server error. cause: " + e.message)
        }
    }
    // follow

    // unfollow

    // profile
}
