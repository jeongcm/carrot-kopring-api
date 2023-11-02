package com.travelit.travelitapi.database.dto

import jakarta.persistence.*
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
data class Token (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Int? = null,

        @Column(nullable = true)
        val accessToken: String? = null,
        @Column(nullable = false)
        val refreshToken: String? = null
)