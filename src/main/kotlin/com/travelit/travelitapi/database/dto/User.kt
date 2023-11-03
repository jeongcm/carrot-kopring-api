package com.travelit.travelitapi.database.dto

import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import lombok.Builder
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
data class User (
        @Column(nullable = false)
        @NotBlank(message = "UserName must not be blank")
        var userName: String,

        @Column(nullable = false)
        @NotBlank(message = "Password must not be blank")
        var password: String,

        @Email
        @Column(nullable = false, unique = true)
        @NotBlank(message = "Email must not be blank")
        var email: String,

        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,
): AuditableEntity() {
}