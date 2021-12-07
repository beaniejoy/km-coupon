package com.kakao.mobility.kmcoupon.config;

import com.kakao.mobility.kmcoupon.common.filter.JwtAuthenticationFilter;
import com.kakao.mobility.kmcoupon.exception.CustomAccessDeniedHandler;
import com.kakao.mobility.kmcoupon.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class ServiceSecurityConfig extends WebSecurityConfigurerAdapter {

    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-valid-in-seconds}")
    private long tokenValidInSeconds;

    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public ServiceSecurityConfig(CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .csrf().disable() // 사이트간 요청 위조, JWT 토큰방식으로 대체
                .formLogin().disable()
                .headers()
                .frameOptions().sameOrigin() // 같은 도메인 내에서는 <iframe> 렌더링 허용

                .and()
                .addFilterBefore(new JwtAuthenticationFilter(jwtTokenUtil()), UsernamePasswordAuthenticationFilter.class) // 인증을 위한 filter 등록(GenericFilterBean과 비교)
                .exceptionHandling()
                .accessDeniedHandler(customAccessDeniedHandler)

                .and()
                .authorizeRequests()
                .antMatchers("/api/v1/coupon").hasAnyRole("USER")

                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 사용 X, 토큰 방식
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(secret, tokenValidInSeconds);
    }
}
