package com.travelit.travelitapi.common.security.oauth2

import com.travelit.travelitapi.common.logger.logger
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
    private var logger = logger()
    @Override
    @Throws(OAuth2AuthenticationException::class)
    override fun loadUser(userRequest: OAuth2UserRequest?): OAuth2User {
        val oAuthUser = super.loadUser(userRequest)

        var registrationId: String = userRequest?.clientRegistration?.registrationId ?: "unknown"
        var userNameAttributeName: String = userRequest?.clientRegistration?.providerDetails?.userInfoEndpoint?.userNameAttributeName ?: "unknown"
        var oAuthAttributes: OAuth2Attribute = OAuth2Attribute.of(registrationId, userNameAttributeName, oAuthUser.attributes)

        var memberAttribute = oAuthAttributes.convertToMap()


        return DefaultOAuth2User(Collections.singleton(SimpleGrantedAuthority("ROLE_USER")), memberAttribute, "email")
    }

//    private fun processOAuth2User(oAuth2UserRequest: OAuth2UserRequest, oAuth2User: OAuth2User) : OAuth2User {
//
//    }
}