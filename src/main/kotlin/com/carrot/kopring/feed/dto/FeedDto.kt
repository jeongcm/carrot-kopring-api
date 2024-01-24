package com.carrot.kopring.feed.dto

import com.carrot.kopring.common.ResultCode
import com.fasterxml.jackson.annotation.JsonInclude
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import java.util.*

data class FeedDto (
        @NotBlank(message = "name must not be blank")
        val name: String,

        val password: String? = null, // 이 필드는 생성시 필수값이 아닌 경우 nullable로 지정

        @Email
        @NotBlank(message = "Email must not be blank")
        val email: String,

        val role: String = "USER",
        val provider: String = "custom"
)