package com.kakao.mobility.kmcoupon.application;

import com.kakao.mobility.kmcoupon.domain.member.SecurityUser;
import com.kakao.mobility.kmcoupon.exception.InvalidRequestException;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;

public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final UserDetailsService userDetailsService;
    private final PasswordEncoder passwordEncoder;

    public CustomAuthenticationProvider(UserDetailsService userDetailsService, PasswordEncoder passwordEncoder) {
        this.userDetailsService = userDetailsService;
        this.passwordEncoder = passwordEncoder;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UsernamePasswordAuthenticationToken token = (UsernamePasswordAuthenticationToken) authentication;

        String email = token.getName();
        String password = (String) token.getCredentials();
        SecurityUser securityUser = (SecurityUser) userDetailsService.loadUserByUsername(email);

        if (passwordEncoder.matches(password, securityUser.getPassword()) == false) {
            throw new InvalidRequestException("비밀번호가 일치하지 않습니다.");
        }

        return new UsernamePasswordAuthenticationToken(securityUser, password, securityUser.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return authentication.equals(UsernamePasswordAuthenticationToken.class);
    }
}
