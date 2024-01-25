package com.carrot.kopring.feed.repository

import com.carrot.kopring.database.entity.Feed
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository : JpaRepository<Feed, Long>, FeedRepositoryCustom {
    fun findByName(name: String): Feed?
    fun findAllByName(name: String?): List<Feed>?
    fun findByEmail(email: String?): Feed?
    fun findAllByEmail(email: String?): List<Feed>?
}
