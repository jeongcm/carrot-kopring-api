package com.travelit.travelitapi.user.service

import com.travelit.travelitapi.database.dto.Token
import com.travelit.travelitapi.user.repository.AccountRepository
import com.travelit.travelitapi.database.dto.Account
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional
import lombok.extern.slf4j.Slf4j
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import kotlin.math.log

@Slf4j
@Service
class AccountService(var accountRepository: AccountRepository, var tokenService: TokenService) {
    val logger: Logger = LoggerFactory.getLogger(AccountService::class.java)
    @Transactional
    fun logIn(user: Account): Token {

        logger.info("hihi")

        // 1. find user and check id, password
        val foundUser = accountRepository.findByName(user.name)
                ?.takeIf { it.password == user.password } ?: throw IllegalArgumentException("아이디 또는 비밀번호가 일치하지 않습니다.")

        logger.info(foundUser.name)
        // need to make token logic
        return tokenService.createToken(foundUser)
    }

    @Transactional
    fun signUp(user: Account): Account {
        val foundUser = accountRepository.findByName(user.name)
            ?.takeIf { it.name == user.name } ?: throw IllegalArgumentException("중복된 이름입니다.")
        return foundUser
//        if (foundUser.name === user.email) {
//            throw IllegalArgumentException("중복된 이름입니다.")
//        }
//        val createUser = Account(user.name, user.password, user.email)
//        // 1. find user and check id, password
//        return accountRepository.save(createUser)
    }
}