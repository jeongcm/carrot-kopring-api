package com.carrot.kopring.database.entity

import com.carrot.kopring.account.dto.FeedDto
import jakarta.persistence.*
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Entity
class Feed (
    @Column(nullable = false)
    var region: String = "KOR", // KOR(Korea), GLO(Global)

    @Column(nullable = true)
    var category: String = "travel", // travel, food, fashion, free

    @Column(nullable = false, unique = true)
    var email: String = "",

    @Column(nullable = false)
    var role: String = "USER",

    @Column(nullable = true)
    var provider: String = "custom",

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
): AuditableEntity() {
    companion object {
        fun from(account: FeedDto) = Account(
            name = account.name,
            password = Base64.getEncoder().encodeToString(account.password?.toByteArray()) ?: "",
            email = account.email,
            role = account.role,
            provider = account.provider
        )
    }

    fun update(account: FeedDto, encoder: PasswordEncoder) {	// 파라미터에 PasswordEncoder 추가
        this.region = account.name
        this.email = account.email
        this.role = account.role
        this.provider = account.provider
    }

    fun delete() {
        this.deletedAt = LocalDateTime.now(ZoneOffset.UTC)
    }
}