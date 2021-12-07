package com.kakao.mobility.kmcoupon.config;

import com.kakao.mobility.kmcoupon.application.CustomAuthenticationProvider;
import com.kakao.mobility.kmcoupon.exception.CustomAccessDeniedHandler;
import com.kakao.mobility.kmcoupon.util.JwtTokenUtil;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@EnableWebSecurity
public class LoginSecurityConfig extends WebSecurityConfigurerAdapter {
    @Value("${jwt.secret}")
    private String secret;
    @Value("${jwt.token-valid-in-seconds}")
    private long tokenValidInSeconds;

    private final UserDetailsService userDetailsService;
    private final CustomAccessDeniedHandler customAccessDeniedHandler;

    public LoginSecurityConfig(UserDetailsService userDetailsService, CustomAccessDeniedHandler customAccessDeniedHandler) {
        this.userDetailsService = userDetailsService;
        this.customAccessDeniedHandler = customAccessDeniedHandler;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
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
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS); // 세션 사용 X, 토큰 방식
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public JwtTokenUtil jwtTokenUtil() {
        return new JwtTokenUtil(secret, tokenValidInSeconds);
    }

    @Bean
    public CustomAuthenticationProvider customAuthenticationProvider() {
        return new CustomAuthenticationProvider(userDetailsService, passwordEncoder());
    }
}