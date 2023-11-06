package com.travelit.travelitapi.user.service

import com.travelit.travelitapi.database.dto.Token
import com.travelit.travelitapi.database.dto.Account
import io.jsonwebtoken.Claims
import io.jsonwebtoken.security.Keys
import io.jsonwebtoken.Jwts
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*

@Component
class TokenService {
    // token expire time
    private val accessTokenExpiredTime = 30 * 60 * 1000L // 30 min
    private val refreshTokenExpiredTime =  7 * 24 * 60 * 60 * 1000L // 1 week

    private val secretKey = "carrot-secret"
    private val issuer = "carrot"
    private val refreshSubject = "refresh"

    private val cookiePath: String = "/"

    private val accessTokenHeader: String = "X-AUTH-ACCESS-TOKEN"

    private val refreshTokenHeader: String = "X-AUTH-REFRESH-TOKEN"
    fun createToken (user: Account): Token {
        val accessToken = createAccessToken(user)
        val refreshToken = createRefreshToken()

        return Token(accessToken, refreshToken)
    }
    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    private fun createAccessToken(user: Account): String {
        val signKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
        val now = Date()
        val expiration = Date(now.time + accessTokenExpiredTime) // 30 min from now

        val claims = setClaims(user.email, issuer, now, expiration)
        return Jwts.builder()
                .header()
                    .add("typ", "JWT")
                    .and()
                .claims(claims)
                .issuedAt(now)
                .signWith(signKey)
                .expiration(expiration)
                .compact()
    }

    private fun createRefreshToken(): String {
        val signKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
        val now = Date()
        val expiration = Date(now.time + refreshTokenExpiredTime) // 30 min from now
        val claims = setClaims(refreshSubject, issuer, now, expiration)
        return Jwts.builder()
                .header()
                .add("typ", "JWT")
                .and()
                .claims(claims)
                .issuedAt(now)
                .signWith(signKey)
                .expiration(expiration)
                .compact()
    }

    private fun setClaims(subject: String, issuer: String, issuedAt: Date, expiration: Date): MutableMap<String, Any> {
        val claims: MutableMap<String, Any> = HashMap()
        claims[Claims.SUBJECT] = subject
        claims[Claims.ISSUER] = issuer
        claims[Claims.ISSUED_AT] = issuedAt
        claims[Claims.EXPIRATION] = expiration

        return claims
    }
}