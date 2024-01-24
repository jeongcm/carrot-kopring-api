package com.carrot.kopring.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.auditing.DateTimeProvider
import org.springframework.data.domain.AuditorAware
import org.springframework.data.jpa.repository.config.EnableJpaAuditing
import org.springframework.security.core.context.SecurityContextHolder
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.util.*

@Configuration
@EnableJpaAuditing(dateTimeProviderRef = "utcDateTimeProvider", auditorAwareRef = "auditorAware")
class JpaAuditingConfig {
    @Bean
    fun utcDateTimeProvider(): DateTimeProvider {
        return DateTimeProvider { Optional.of(LocalDateTime.now(ZoneOffset.UTC)) }
    }

    @Bean
    fun auditorAware(): AuditorAware<String> {
        return AuditorAware { Optional.of(SecurityContextHolder.getContext().authentication?.name ?: "system") }
    }
}