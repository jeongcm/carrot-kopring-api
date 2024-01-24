package com.carrot.kopring.feed.repository.queryDsl

import com.carrot.kopring.feed.repository.FeedRepositoryCustom
import com.carrot.kopring.database.entity.Account
import com.carrot.kopring.database.entity.QAccount
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository

@Repository
class FeedRepositoryImpl(val jpaQueryFactory: JPAQueryFactory) : FeedRepositoryCustom {
    override fun findByRole(role: String): List<Account> {
        val query = jpaQueryFactory.selectFrom(QAccount.account).where(QAccount.account.role.eq(role))
        return query.fetch()
    }
}