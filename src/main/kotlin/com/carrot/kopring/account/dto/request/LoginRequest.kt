package com.carrot.kopring.account.dto.request

import com.carrot.kopring.account.dto.AccountDto
import com.carrot.kopring.database.entity.Account
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.*

data class LoginRequest(
        @NotBlank(message = "name must not be blank")
        val name: String,

        val password: String? = null,

        @Email
        @NotBlank(message = "Email must not be blank")
        val email: String
)
