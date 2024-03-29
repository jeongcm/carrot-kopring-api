package com.carrot.kopring.account.service

import com.carrot.kopring.account.dto.AccountDto
import com.carrot.kopring.common.security.UserDetailsServiceImpl
import com.carrot.kopring.account.dto.TokenDto
import io.jsonwebtoken.Claims
import io.jsonwebtoken.JwtParser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.security.Keys
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.userdetails.UserDetails
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
    private val signKey = Keys.hmacShaKeyFor(secretKey.toByteArray(StandardCharsets.UTF_8))

    val accessTokenHeader: String = "X-AUTH-ACCESS-TOKEN"

    val refreshTokenHeader: String = "X-AUTH-REFRESH-TOKEN"
    fun createToken (account: AccountDto): TokenDto {
        val accessToken = createAccessToken(account)
        val refreshToken = createRefreshToken(account)
        return TokenDto(accessToken, refreshToken)
    }
    // 객체 초기화, secretKey를 Base64로 인코딩한다.
    private fun createAccessToken(account: AccountDto): String {
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

    private fun createRefreshToken(account: AccountDto): String {
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

    fun parseSubject(token: String): String {
        val claims: Claims = getClaims(token)
        return claims[Claims.SUBJECT] as String
    }
    fun getAuthentication(email: String): Authentication {
        val userDetails: UserDetails = userDetailsService.loadUserByEmail(email)

        return UsernamePasswordAuthenticationToken(userDetails, null, userDetails.authorities)
    }

    private fun setClaims(account: AccountDto, issuer: String, issuedAt: Date, expiration: Date): MutableMap<String, Any> {
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
        return parser.parseSignedClaims(token).payload
    }
}