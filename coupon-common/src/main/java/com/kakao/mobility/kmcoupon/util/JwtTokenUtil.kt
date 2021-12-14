package com.kakao.mobility.kmcoupon.util

import com.kakao.mobility.kmcoupon.domain.member.SecurityUser
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import mu.KLogging
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.Authentication
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import java.security.Key
import java.util.*

class JwtTokenUtil(secret: String, tokenValidInSeconds: Long) {
    private val key: Key
    private val tokenValidInSeconds: Long

    companion object : KLogging() {
        private const val AUTHORITIES_KEY = "authorities"
    }

    init {
        key = Keys.hmacShaKeyFor(secret.toByteArray())
        this.tokenValidInSeconds = tokenValidInSeconds
    }

    fun createToken(authentication: Authentication): String {
        val principal = authentication.principal as SecurityUser
        val authorities = authentication.authorities.joinToString(separator = ",") { it.authority }
        val now = Date().time
        val expiredDate = Date(now + tokenValidInSeconds * 1000)
        return Jwts.builder()
            .setSubject(principal.username)
            .claim("memberId", principal.memberId.toString())
            .claim(AUTHORITIES_KEY, authorities)
            .signWith(key, SignatureAlgorithm.HS256)
            .setExpiration(expiredDate)
            .compact()
    }

    fun getAuthentication(token: String?): Authentication {
        val claims = Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body

        val authorities: Collection<GrantedAuthority> =
            claims.get(AUTHORITIES_KEY, String::class.java)
                .split(",")
                .toList()
                .map { SimpleGrantedAuthority(it) }

        val principal = SecurityUser(
            memberId = claims.get("memberId", String::class.java).toLong(),
            username = claims.subject,
            password = "",
            authorities = authorities
        )
        return UsernamePasswordAuthenticationToken(principal, token, authorities)
    }

    fun validateJwtToken(token: String?): Boolean {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token)
            return true
        } catch (e: RuntimeException) {
            logger.info("################ 유효하지 않은 JWT 토큰입니다. 다시 발급해주세요. ################")
        }
        return false
    }


}