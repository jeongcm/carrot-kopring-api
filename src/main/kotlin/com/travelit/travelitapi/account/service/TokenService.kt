package com.travelit.travelitapi.account.service

import com.travelit.travelitapi.common.security.UserDetailsServiceImpl
import com.travelit.travelitapi.database.dto.Account
import com.travelit.travelitapi.database.dto.Token
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Component
import java.nio.charset.StandardCharsets
import java.util.*


@Component
class TokenService(private val userDetailsService: UserDetailsServiceImpl) {
    // token expire time
    private val accessTokenExpiredTime = 30 * 60 * 1000L // 30 min
    private val refreshTokenExpiredTime =  7 * 24 * 60 * 60 * 1000L // 1 week

    // auto generated uuid
    private val secretKey = "ba072a510e8444cd95cfe75900529619"
    private val issuer = "carrot"
    private val refreshSubject = "refresh"
    private val signKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    private val cookiePath: String = "/"

    private val accessTokenHeader: String = "X-AUTH-ACCESS-TOKEN"

    private val refreshTokenHeader: String = "X-AUTH-REFRESH-TOKEN"
    fun createToken (account: Account): Token {
        val accessToken = createAccessToken(account)
        val refreshToken = createRefreshToken(account)

        return Token(accessToken, refreshToken)
    }
    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    private fun createAccessToken(account: Account): String {
        val now = Date()
        val expiration = Date(now.time + accessTokenExpiredTime) // 30 min from now

        val claims = setClaims(account, issuer, now, expiration)
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

    private fun createRefreshToken(account: Account): String {
        val signKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))
        val now = Date()
        val expiration = Date(now.time + refreshTokenExpiredTime) // 30 min from now
        val claims = setClaims(account, issuer, now, expiration)
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

    fun validateToken(token: String): Boolean {
        val claims: Claims = getClaims(token)
        val exp: Date = claims.expiration
        val now = Date()
        return exp.after(now)
    }

    fun parseUsername(token: String): String {
        val claims: Claims = getClaims(token)
        return claims["username"] as String
    }

    fun getAuthentication(name: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByUsername(name)

        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun setClaims(account: Account, issuer: String, issuedAt: Date, expiration: Date): MutableMap<String, Any> {
        val claims: MutableMap<String, Any> = HashMap()
        claims["username"] = account.name
        claims[Claims.SUBJECT] = account.email
        claims[Claims.ISSUER] = issuer
        claims[Claims.ISSUED_AT] = issuedAt
        claims[Claims.EXPIRATION] = expiration

        return claims
    }

    // 모든 Claims 조회
    private fun getClaims(token: String): Claims {
        val parser: JwtParser = Jwts.parser().verifyWith(signKey).build()
        return parser.parseUnsecuredClaims(token).payload
    }
}