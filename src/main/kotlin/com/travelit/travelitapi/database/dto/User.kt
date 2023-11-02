package com.travelit.travelitapi.database.dto

import jakarta.persistence.Column
import jakarta.persistence.Entity
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import lombok.NoArgsConstructor


@Entity
@NoArgsConstructor
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Int? = null,

    @Column(nullable = false, unique = true)
    val userName: String,
    @Column(nullable = false)
    val password: String
)