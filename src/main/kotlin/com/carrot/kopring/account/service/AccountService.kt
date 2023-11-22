package com.carrot.kopring.account.service

import com.carrot.kopring.database.NotFoundEntityException
import com.carrot.kopring.database.dto.Token
import com.carrot.kopring.account.repository.AccountRepository
import com.carrot.kopring.database.dto.Account
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder

@Service
class AccountService(
    private val accountRepository: com.carrot.kopring.account.repository.AccountRepository,
    private val tokenService: com.carrot.kopring.account.service.TokenService,
    private val encoder: PasswordEncoder,
    ) {
    @Transactional
    fun logIn(account: Account): Token {
        accountRepository.findAllByEmail(account.email)?.forEach {
            if (it.name == account.name) {
                return tokenService.createToken(account)
            }
        }

        throw NotFoundEntityException("사용자를 찾을 수 없습니다.")
    }

    @Transactional
    fun signUp(account: Account): Account {
        accountRepository.findAllByName(account.name)?.forEach {
            if (it.email == account.email) {
                throw IllegalArgumentException("이미 등록된 이메일입니다.")
            }
        }

        // 1. find user and check id, password
        return accountRepository.save(Account.from(account, encoder))
    }
}