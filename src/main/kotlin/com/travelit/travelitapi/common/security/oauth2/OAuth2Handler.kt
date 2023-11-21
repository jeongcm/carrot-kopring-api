package com.travelit.travelitapi.common.security.oauth2

import com.travelit.travelitapi.account.repository.AccountRepository
import com.travelit.travelitapi.account.service.TokenService
import com.travelit.travelitapi.database.dto.Account
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.http.MediaType
import org.springframework.security.core.Authentication
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.security.web.authentication.AuthenticationSuccessHandler
import org.springframework.stereotype.Component

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
        response.contentType = MediaType.TEXT_HTML_VALUE
        response.addHeader(tokenService.accessTokenHeader, token.accessToken)
        response.addHeader(tokenService.refreshTokenHeader, token.refreshToken)
        response.contentType = MediaType.APPLICATION_JSON_VALUE

//        val writer = response.writer
//        writer.println(ObjectMapper().writeValueAsString(token))
//        writer.flush()
    }
}