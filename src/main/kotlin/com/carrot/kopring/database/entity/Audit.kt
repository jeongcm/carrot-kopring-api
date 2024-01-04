package com.carrot.kopring.database.entity

import org.springframework.data.annotation.CreatedBy
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedBy
import org.springframework.data.annotation.LastModifiedDate
import java.util.Date
import jakarta.persistence.EntityListeners
import jakarta.persistence.MappedSuperclass
import org.springframework.data.jpa.domain.support.AuditingEntityListener

@MappedSuperclass
@EntityListeners(AuditingEntityListener::class)
open class AuditableEntity {
    @CreatedBy
    var createdBy: String? = null

    @CreatedDate
    var createdDate: Date? = null

    @LastModifiedBy
    var updatedBy: String? = null

    @LastModifiedDate
    var updatedAt: Date? = null
}