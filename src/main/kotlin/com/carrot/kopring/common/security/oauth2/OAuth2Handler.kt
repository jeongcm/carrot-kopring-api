package com.carrot.kopring.common.security.oauth2

import com.carrot.kopring.account.repository.AccountRepository
import com.carrot.kopring.account.service.TokenService
import com.carrot.kopring.common.logger.logger
import com.carrot.kopring.database.dto.Account
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.oidc.user.OidcUser
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets

@RequiredArgsConstructor
@Component
class OAuth2SuccessHandler(val accountRepository: AccountRepository, val tokenService: TokenService) : AuthenticationSuccessHandler {

    @Override
    override fun onAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication : Authentication) {
        // oauth 인증 성공시 회원가입 or 로그인
        val oAuth2User: OAuth2User = authentication.principal as OAuth2User

        val account = Account().apply {
            name = oAuth2User.attributes["name"] as String
            email = oAuth2User.attributes["email"] as String
            provider = oAuth2User.attributes["provider"] as String
        }
        var existFlag = false
        accountRepository.findAllByName(account.name)?.forEach {
            if (it.email == account.email) {
                //회원이 존재
                existFlag = true
            }
        }

        if (!existFlag) {
            //회원이 아니면 회원 가입
            accountRepository.save(account)
        }

        // 토큰 발행
        val token = tokenService.createToken(account)

        // response에 토큰 담아서 보냄
        response.contentType = MediaType.TEXT_HTML_VALUE+";"+StandardCharsets.UTF_8.name()
        response.addHeader(tokenService.accessTokenHeader, token.accessToken)
        response.addHeader(tokenService.refreshTokenHeader, token.refreshToken)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
        // 어디로 redirect 할지 설정
//        response.sendRedirect("/home")
    }

    fun odicOnAuthenticationSuccess(request: HttpServletRequest, response: HttpServletResponse, authentication : Authentication) {
        // oauth 인증 성공시 회원가입 or 로그인
//        val oAuth2User: OAuth2User = authentication.principal as OAuth2User
        val oidcUser: OidcUser = authentication.principal as OidcUser
        logger().info(oidcUser.claims.toString())
        val account = Account().apply {
            name = oidcUser.claims["name"] as String
            email = oidcUser.claims["email"] as String
//            provider = oAuth2User.attributes["provider"] as String
        }
        var existFlag = false
        accountRepository.findAllByName(account.name)?.forEach {
            if (it.email == account.email) {
                //회원이 존재
                existFlag = true
            }
        }

        if (!existFlag) {
            //회원이 아니면 회원 가입
            accountRepository.save(account)
        }

        // 토큰 발행
        val token = tokenService.createToken(account)

        // response에 토큰 담아서 보냄
        response.contentType = MediaType.TEXT_HTML_VALUE+";"+StandardCharsets.UTF_8.name()
        response.addHeader(tokenService.accessTokenHeader, token.accessToken)
        response.addHeader(tokenService.refreshTokenHeader, token.refreshToken)
        response.contentType = MediaType.APPLICATION_JSON_VALUE
    }
}