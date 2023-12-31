package com.carrot.kopring.database.dto

import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@NoArgsConstructor
@AllArgsConstructor
data class Token (
        val accessToken: String? = null,
        val refreshToken: String? = null
)