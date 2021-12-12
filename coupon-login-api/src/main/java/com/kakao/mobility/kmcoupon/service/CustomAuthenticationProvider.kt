package com.kakao.mobility.kmcoupon.service

import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.password.PasswordEncoder
import kotlin.Throws
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import com.kakao.mobility.kmcoupon.domain.member.SecurityUser
import com.kakao.mobility.kmcoupon.exception.InvalidRequestException
import org.springframework.security.authentication.AuthenticationProvider
import org.springframework.security.core.Authentication
import org.springframework.security.core.AuthenticationException

class CustomAuthenticationProvider(
    private val userDetailsService: UserDetailsService,
    private val passwordEncoder: PasswordEncoder
) : AuthenticationProvider {
    @Throws(AuthenticationException::class)
    override fun authenticate(authentication: Authentication): Authentication {
        val token = authentication as UsernamePasswordAuthenticationToken
        val email = token.name
        val password = token.credentials as String
        val securityUser = userDetailsService.loadUserByUsername(email) as SecurityUser
        if (!passwordEncoder.matches(password, securityUser.password)) {
            throw InvalidRequestException("비밀번호가 일치하지 않습니다.")
        }

        return UsernamePasswordAuthenticationToken(securityUser, password, securityUser.authorities)
    }

    override fun supports(authentication: Class<*>): Boolean {
        return authentication == UsernamePasswordAuthenticationToken::class.java
    }
}