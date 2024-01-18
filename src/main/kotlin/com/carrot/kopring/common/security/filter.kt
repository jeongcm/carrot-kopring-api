package com.carrot.kopring.common.security

import com.carrot.kopring.account.service.TokenService
import io.jsonwebtoken.JwtException
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServlet
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpHeaders
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.stereotype.Component
import org.springframework.web.filter.OncePerRequestFilter

@Component
class JwtAuthenticationFilter(private val tokenService: TokenService) : OncePerRequestFilter() {
    override fun doFilterInternal(request: HttpServletRequest, response: HttpServletResponse, filterChain: FilterChain) {
        try {
            // 헤더에 Authorization이 있다면 가져온다.
            val authorizationHeader: String? = request.getHeader("Authorization") ?: return filterChain.doFilter(request, response)
            // Bearer타입 토큰이 있을 때 가져온다.
            val token = authorizationHeader?.substring("Bearer ".length) ?: return filterChain.doFilter(request, response)
            // 토큰 검증
            if (tokenService.validateToken(token)) {
                // 토큰에서 username 파싱
                val email = tokenService.parseSubject(token)
                // email으로 AuthenticationToken 생성
                val authentication: Authentication = tokenService.getAuthentication(email)
                // 생성된 AuthenticationToken을 SecurityContext가 관리하도록 설정
                SecurityContextHolder.getContext().authentication = authentication
            }
        } catch (e: JwtException) {
            response.status = HttpServletResponse.SC_UNAUTHORIZED
            response.contentType = MediaType.APPLICATION_JSON_VALUE
        }

        filterChain.doFilter(request, response)
    }

    private fun parseBearerToken(request: HttpServletRequest) = request.getHeader(HttpHeaders.AUTHORIZATION)
        .takeIf { it?.startsWith("Bearer ", true) ?: false }?.substring(7)
}