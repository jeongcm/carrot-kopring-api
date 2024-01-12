package com.carrot.kopring.database.entity

import com.carrot.kopring.account.dto.AccountDto
import jakarta.persistence.*
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import lombok.NoArgsConstructor
import org.springframework.security.crypto.password.PasswordEncoder

@Entity
data class Account (
        @Column(nullable = false)
        var name: String = "",

        @Column(nullable = true)
        var password: String = "",

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
                fun from(account: AccountDto, encoder: PasswordEncoder) = Account(
                        name = account.name,
                        password = encoder.encode(account.password) ?: "",
                        email = account.email,
                        role = account.role,
                        provider = account.provider
                )
        }

        fun update(account: AccountDto, encoder: PasswordEncoder) {	// 파라미터에 PasswordEncoder 추가
                this.password = account.password
                        .takeIf { it?.isNotBlank() ?: true }
                        ?.let { encoder.encode(it) }	// 추가
                        ?: this.password
                this.name = account.name
                this.email = account.email
                this.role = account.role
                this.provider = account.provider
        }
}