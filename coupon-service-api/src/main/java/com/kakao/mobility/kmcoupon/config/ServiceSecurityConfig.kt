package com.kakao.mobility.kmcoupon.config

import com.kakao.mobility.kmcoupon.common.filter.JwtAuthenticationFilter
import com.kakao.mobility.kmcoupon.exception.CustomAccessDeniedHandler
import com.kakao.mobility.kmcoupon.util.JwtTokenUtil
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
class ServiceSecurityConfig(
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
            .frameOptions().sameOrigin() // 같은 도메인 내에서는 <iframe> 렌더링 허용

            .and()
            .addFilterBefore(
                JwtAuthenticationFilter(jwtTokenUtil()),
                UsernamePasswordAuthenticationFilter::class.java
            ) // 인증을 위한 filter 등록(GenericFilterBean과 비교)
            .exceptionHandling()
            .accessDeniedHandler(customAccessDeniedHandler)

            .and()
            .authorizeRequests()
            .antMatchers("/api/v1/coupons**").hasAnyRole("USER")
            .anyRequest().permitAll()

            .and()
            .sessionManagement()
            .sessionCreationPolicy(SessionCreationPolicy.STATELESS) // 세션 사용 X, 토큰 방식
    }

    @Bean
    fun jwtTokenUtil(): JwtTokenUtil {
        return JwtTokenUtil(secret, tokenValidInSeconds.toLong())
    }
}