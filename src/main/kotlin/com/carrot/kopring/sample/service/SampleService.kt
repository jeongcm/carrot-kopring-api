package com.carrot.kopring.sample.service

import com.carrot.kopring.account.repository.AccountRepository
import com.carrot.kopring.database.entity.Account
import org.springframework.stereotype.Service

@Service
class SampleService(private val accountRepository: AccountRepository) {
    fun getSampleTest(role: String) : List<Account> {
        return this.accountRepository.findByRole(role)
    }
}