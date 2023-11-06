package com.travelit.travelitapi.account.service

import com.travelit.travelitapi.database.NotFoundEntityException
import com.travelit.travelitapi.database.dto.Token
import com.travelit.travelitapi.account.repository.AccountRepository
import com.travelit.travelitapi.database.dto.Account
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional

@Service
class AccountService(var accountRepository: AccountRepository, var tokenService: TokenService) {
    @Transactional
    fun logIn(account: Account): Token {
        // 1. find user and check id, password
        val foundUser = accountRepository.findByName(account.name) ?: throw NotFoundEntityException("사용자를 찾을 수 없습니다.")

        //

        // need to make token logic
        return tokenService.createToken(foundUser)
    }

    @Transactional
    fun signUp(account: Account): Account {

        val foundUser = accountRepository.findByName(account.name)

        // 중복된 계정입니다.
        if (foundUser != null && (foundUser.email == account.email)) {
                throw IllegalArgumentException("중복된 계정입니다.")
        }

        // 1. find user and check id, password
        return accountRepository.save(account)
    }
}