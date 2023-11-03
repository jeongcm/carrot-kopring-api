package com.travelit.travelitapi.database.dto

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import lombok.NoArgsConstructor

@Entity
@NoArgsConstructor
data class User (
        @NotBlank(message = "Password must not be blank")
        var userName: String,
        @Column(nullable = false)
        var password: String,

): AuditableEntity() {}