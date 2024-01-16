package com.carrot.kopring.common.security.oauth2

import com.carrot.kopring.account.repository.AccountRepository
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import lombok.RequiredArgsConstructor
import org.springframework.security.core.AuthenticationException
import org.springframework.security.web.authentication.AuthenticationFailureHandler
import org.springframework.stereotype.Component

@RequiredArgsConstructor
@Component
class OAuth2FailureHandler(val accountRepository: AccountRepository) : AuthenticationFailureHandler {
    override fun onAuthenticationFailure(
        request: HttpServletRequest?,
        response: HttpServletResponse,
        exception: AuthenticationException?
    ) {
        response.status = HttpServletResponse.SC_BAD_REQUEST
        response.writer.write("소셜 로그인 실패! 서버 로그를 확인해주세요.")
    }
}
