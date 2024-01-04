package com.carrot.kopring.account.dto

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@NoArgsConstructor
@AllArgsConstructor
data class TokenDto (
        val accessToken: String? = null,
        val refreshToken: String? = null
)