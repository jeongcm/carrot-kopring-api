package com.carrot.kopring.feed.repository

import com.carrot.kopring.database.entity.Account
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository : JpaRepository<Account, Long>, FeedRepositoryCustom {
    fun findByName(name: String): Account?
    fun findAllByName(name: String?): List<Account>?
    fun findByEmail(email: String?): Account?
    fun findAllByEmail(email: String?): List<Account>?
}
