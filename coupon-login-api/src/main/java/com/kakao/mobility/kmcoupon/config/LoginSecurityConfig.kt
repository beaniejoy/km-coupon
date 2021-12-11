package com.kakao.mobility.kmcoupon.config

import com.kakao.mobility.kmcoupon.application.CustomAuthenticationProvider
import com.kakao.mobility.kmcoupon.exception.CustomAccessDeniedHandler
import com.kakao.mobility.kmcoupon.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder

@EnableWebSecurity
class LoginSecurityConfig(
    private val userDetailsService: UserDetailsService,
    private val customAccessDeniedHandler: CustomAccessDeniedHandler
) : WebSecurityConfigurerAdapter() {
    @Value("\${jwt.secret}")
    private lateinit var secret: String

    @Value("\${jwt.token-valid-in-seconds}")
    private lateinit var tokenValidInSeconds: String

    @Throws(Exception::class)
    override fun configure(http: HttpSecurity) {
        http
            .csrf().disable() // 사이트간 요청 위조, JWT 토큰방식으로 대체
            .formLogin().disable()
            .headers()
            .frameOptions().sameOrigin()
            .and()
            .exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler)
            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 X, 토큰 방식
    }

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun jwtTokenUtil(): JwtTokenUtil {
        return JwtTokenUtil(secret, tokenValidInSeconds.toLong())
    }

    @Bean
    fun customAuthenticationProvider(): CustomAuthenticationProvider {
        return CustomAuthenticationProvider(userDetailsService, passwordEncoder())
    }
}