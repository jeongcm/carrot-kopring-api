package com.travelit.travelitapi.account.repository

import com.travelit.travelitapi.database.dto.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface AccountRepository : JpaRepository<Account, Long> {
    fun findByName(name: String): Account?
    fun findAllByName(name: String?): List<Account>?
    fun findByEmail(email: String?): Account?
    fun findAllByEmail(email: String?): List<Account>?
}
