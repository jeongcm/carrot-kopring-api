package com.travelit.travelitapi.database.dto

import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import lombok.AllArgsConstructor
import lombok.NoArgsConstructor
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.util.Date


@Entity
data class User (
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val id: Long? = null,

        @NotBlank(message = "Password must not be blank")
        var userName: String,
        @Column(nullable = false)
        var password: String,

): AuditableEntity() {}