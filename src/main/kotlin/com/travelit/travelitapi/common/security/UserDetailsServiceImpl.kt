package com.travelit.travelitapi.common.security

import com.travelit.travelitapi.account.repository.AccountRepository
import com.travelit.travelitapi.database.NotFoundEntityException
import com.travelit.travelitapi.database.dto.Account
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class UserDetailsServiceImpl(private val accountRepository: AccountRepository) : UserDetailsService {
    override fun loadUserByUsername(name: String): UserDetails {
        val account: Account = accountRepository.findByName(name) ?: throw NotFoundEntityException("존재하지 않는 사용자 ${name}입니다.")

        return UserDetailsImpl(account)
    }
}