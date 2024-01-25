package com.carrot.kopring.feed.repository

import com.carrot.kopring.database.entity.Feed
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface FeedRepository : JpaRepository<Feed, Long>, FeedRepositoryCustom {
}
