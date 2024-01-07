package com.carrot.kopring.common.security.oauth2

import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest
import org.springframework.security.oauth2.core.OAuth2AuthenticationException
import org.springframework.security.oauth2.core.user.DefaultOAuth2User
import org.springframework.security.oauth2.core.user.OAuth2User
import org.springframework.stereotype.Service
import java.util.*
import kotlin.jvm.Throws

@Service
class CustomOAuth2UserService : DefaultOAuth2UserService() {

    /**
     * Spring Security가 access token을 이용해서 OAuth2 Server에서 유저 정보를 가져온 다음 loadUser 메서드를 통해 유저의 정보를 가져옵니다.
     */
    @Override
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuthUser = super.loadUser(userRequest) // oAuth 서비스(kakao, google, naver)에서 가져온 유저 정보 담고 있음

        // OAuth 서비스 이름(ex. kakao, naver, google)
        val registrationId: String = userRequest?.clientRegistration?.registrationId ?: "unknown"
        // OAuth 로그인 시 키(pk)가 되는 값 ( 근데 "email" 로 설정함? )
        val userNameAttributeName: String = userRequest?.clientRegistration?.providerDetails?.userInfoEndpoint?.userNameAttributeName ?: "unknown"
        // registrationId에 따라 유저 정보를 통해 공통된 유저 정보 객체를 만들어줌
        val oAuthAttributes: OAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuthUser.attributes)

        // oauth attribute 세팅
        val memberAttribute = oAuthAttributes.convertToMap().toMutableMap()
        memberAttribute.put("provider", registrationId)

        return DefaultOAuth2User(Collections.singleton(SimpleGrantedAuthority("ROLE_USER")), memberAttribute, "email")
    }
}
