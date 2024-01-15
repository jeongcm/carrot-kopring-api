package com.carrot.kopring.account.repository.queryDsl

import com.carrot.kopring.account.repository.AccountRepositoryCustom
import com.carrot.kopring.database.entity.Account
import com.carrot.kopring.database.entity.QAccount
import com.querydsl.jpa.impl.JPAQueryFactory
import org.springframework.stereotype.Repository


@Repository
class AccountImpl(val jpaQueryFactory: JPAQueryFactory) : AccountRepositoryCustom {
    override fun findByRole(role: String): List<Account> {
        val query = jpaQueryFactory.selectFrom(QAccount.account).where(QAccount.account.role.eq(role))
        return query.fetch()
    }
}