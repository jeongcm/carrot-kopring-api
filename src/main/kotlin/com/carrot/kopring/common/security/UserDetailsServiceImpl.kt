package com.carrot.kopring.common.security

import com.carrot.kopring.database.entity.Account
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val accountRepository: com.carrot.kopring.account.repository.AccountRepository) : UserDetailsService {
    override fun loadUserByUsername(name: String): UserDetails {
        val account: Account = accountRepository.findByName(name) ?: throw UsernameNotFoundException("존재하지 않는 사용자 ${name}입니다.")

        return UserDetailsImpl(account)
    }

    fun loadUserByEmail(email: String): UserDetails {
        val account: Account = accountRepository.findByEmail(email) ?: throw UsernameNotFoundException("존재하지 않는 사용자 ${email}입니다.")

        return UserDetailsImpl(account)
    }
}