package com.travelit.travelitapi.database.dto

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
@AllArgsConstructor
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @NotBlank(message = "Password must not be blank")
        var userName: String,
        @Column(nullable = false)
        var password: String,

): AuditableEntity() {}