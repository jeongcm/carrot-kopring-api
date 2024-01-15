package com.carrot.kopring.account.repository

import com.carrot.kopring.database.entity.Account

interface AccountRepositoryCustom {
    fun findByRole(email: String): Account?
}