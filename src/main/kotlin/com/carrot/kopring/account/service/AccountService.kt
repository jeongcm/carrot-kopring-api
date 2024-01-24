package com.carrot.kopring.account.service

import com.carrot.kopring.account.dto.AccountDto
import com.carrot.kopring.database.NotFoundEntityException
import com.carrot.kopring.account.dto.TokenDto
import com.carrot.kopring.account.repository.AccountRepository
import com.carrot.kopring.database.InvalidRequestException
import com.carrot.kopring.database.entity.Account
import org.springframework.stereotype.Service
import jakarta.transaction.Transactional
import org.springframework.security.crypto.password.PasswordEncoder
import java.util.Base64.getEncoder

@Service
@Transactional
class AccountService(
    private val accountRepository: AccountRepository,
    private val tokenService: TokenService,
    private val encoder: PasswordEncoder,
) {
    fun logIn(name: String, email: String, password: String?): TokenDto {
        accountRepository.findAllByEmail(email)?.forEach {
            if (it.name == name) {
                val pwd = getEncoder().encodeToString(password!!.toByteArray())
                if (pwd  != it.password) {
                    throw InvalidRequestException("비밀 번호가 일치하지 않습니다.");
                }
               val account = AccountDto(
                       name = name,
                       email = email,
                       password = password
               )
                return tokenService.createToken(account)
            }
        }

        throw NotFoundEntityException("사용자를 찾을 수 없습니다.")
    }

    fun signUp(account: AccountDto): Account {
        accountRepository.findAllByName(account.name)?.forEach {
            if (it.email == account.email) {
                throw IllegalArgumentException("이미 등록된 이메일입니다.")
            }
        }

        // 1. find user and check id, password
        return accountRepository.save(Account.from(account, encoder))
    }
}
