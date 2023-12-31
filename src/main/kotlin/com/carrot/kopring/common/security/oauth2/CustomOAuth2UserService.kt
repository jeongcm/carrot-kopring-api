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
    @Override
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuthUser = super.loadUser(userRequest)

        val registrationId: String = userRequest?.clientRegistration?.registrationId ?: "unknown"
        val userNameAttributeName: String = userRequest?.clientRegistration?.providerDetails?.userInfoEndpoint?.userNameAttributeName ?: "unknown"
        val oAuthAttributes: OAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuthUser.attributes)

        // oauth attribute 세팅
        val memberAttribute = oAuthAttributes.convertToMap().toMutableMap()
        memberAttribute.put("provider", registrationId)

        return DefaultOAuth2User(Collections.singleton(SimpleGrantedAuthority("ROLE_USER")), memberAttribute, "email")
    }
}