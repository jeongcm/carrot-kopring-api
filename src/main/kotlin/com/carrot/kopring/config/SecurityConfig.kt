package com.carrot.kopring.config

import com.carrot.kopring.common.security.JwtAuthenticationFilter
import com.carrot.kopring.common.security.oauth2.CustomOAuth2UserService
import com.carrot.kopring.common.security.oauth2.OAuth2SuccessHandler
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@Configuration
@EnableWebSecurity
class SecurityConfig(val jwtFilter: JwtAuthenticationFilter, val customOAuth2UserService: CustomOAuth2UserService, val successHandler: OAuth2SuccessHandler) {
    @Bean
    fun filterChain(http: HttpSecurity) = http
        .csrf{
            it.disable()  // csrf 설정하지 않음
        }
        .authorizeHttpRequests {
            it.requestMatchers("/","/auth/login", "/auth/login/**", "/swagger-ui/**", "/test/**").permitAll() // 로그인 회원가입 관련 router 와 / 는 인증 제외
            it.requestMatchers("/actuator","/actuator/**").permitAll() // prometheus test
            it.requestMatchers("/auth/admin").hasRole("ADMIN") // only admin user
                .anyRequest().authenticated() // 그 외 모든 요청은 인증 필요
        }
        .formLogin {

        }
        .oauth2Login {
            it.userInfoEndpoint{ userInfoIt ->
                userInfoIt.userService(customOAuth2UserService)
            }
            it.successHandler(successHandler)
        }
        .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.NEVER) }
        .addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter::class.java)
        .build()!! // !! 은 null이 아님을 보장

    @Bean
    fun passwordEncoder() = BCryptPasswordEncoder()
}