package com.carrot.kopring.database.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
class AuditableEntity {
    @CreatedBy
    var createdBy: String? = null

    @CreatedDate
    var createdAt: LocalDateTime? = null

    @LastModifiedBy
    var updatedBy: String? = null

    @LastModifiedDate
    var updatedAt: LocalDateTime? = null

    var deletedAt: LocalDateTime? = null
}