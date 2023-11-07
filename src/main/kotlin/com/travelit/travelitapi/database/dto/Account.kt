package com.travelit.travelitapi.database.dto

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import lombok.Builder
import lombok.NoArgsConstructor

enum class Role() {
        ADMIN, USER
}

@Entity
@NoArgsConstructor
data class Account (
        @Column(nullable = false)
        @NotBlank(message = "name must not be blank")
        var name: String,

        @Column(nullable = false)
        @NotBlank(message = "password must not be blank")
        var password: String,

        @Email
        @Column(nullable = false, unique = true)
        @NotBlank(message = "Email must not be blank")
        var email: String,

        @Enumerated(EnumType.STRING)
        @Column(nullable = false)
        var role: Role = Role.USER,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
): AuditableEntity() {
        constructor() : this("", "", "", Role.USER)
}