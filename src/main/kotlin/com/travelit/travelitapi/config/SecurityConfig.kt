package com.travelit.travelitapi.config

import com.travelit.travelitapi.common.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(val jwtFilter: JwtAuthenticationFilter) {
    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf{
            it.disable()  // csrf 설정하지 않음
        }
        .authorizeHttpRequests {
            it.requestMatchers("/","/auth/logIn", "/auth/signUp").permitAll() // 로그인 회원가입 관련 router 와 / 는 인증 제외
                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) } // 세션을 사용하지 않음
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        .build()!! // !! 은 null이 아님을 보장

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}