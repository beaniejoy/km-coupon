package com.kakao.mobility.kmcoupon.application

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.core.Authentication
import org.springframework.stereotype.Service

@Service
class AuthService(
    private val authenticationManagerBuilder: AuthenticationManagerBuilder
) {
    fun authenticateMember(email: String, password: String): Authentication {
        val authenticationToken = UsernamePasswordAuthenticationToken(email, password)
        return authenticationManagerBuilder.getObject().authenticate(authenticationToken)
    }
}