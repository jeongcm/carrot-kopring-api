package com.carrot.kopring.account.controller

import com.carrot.kopring.account.dto.AccountDto
import com.carrot.kopring.database.NotFoundEntityException
import com.carrot.kopring.account.dto.TokenDto
import com.carrot.kopring.account.service.AccountService
import com.carrot.kopring.account.service.TokenService
import com.carrot.kopring.common.logger.logger
import jakarta.validation.Valid
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.nio.charset.StandardCharsets

@RestController
@RequestMapping("/auth")
class AccountController(var accountService: AccountService, var tokenService: TokenService) {
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
    fun login(@RequestBody @Valid account: AccountDto): ResponseEntity<Any> {
        return try {
            val token: TokenDto = accountService.logIn(account)

            val headers = HttpHeaders()
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.TEXT_HTML_VALUE+";"+StandardCharsets.UTF_8.name()) // Content-Type 설정
            headers.add(tokenService.accessTokenHeader, token.accessToken)
            headers.add(tokenService.refreshTokenHeader, token.refreshToken)
            headers.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE) // Content-Type 설정

            return ResponseEntity(null, headers, HttpStatus.OK)

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
    fun signUp(@RequestBody @Valid account: AccountDto, accountRepository: com.carrot.kopring.account.repository.AccountRepository): ResponseEntity<Any> {
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
