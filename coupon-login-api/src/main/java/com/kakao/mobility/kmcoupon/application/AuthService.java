package com.kakao.mobility.kmcoupon.application;

import com.kakao.mobility.kmcoupon.dto.request.LoginRequest;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthService {
    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    public AuthService(AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    public Authentication authenticateMember(LoginRequest loginRequest) {
        UsernamePasswordAuthenticationToken authenticationToken
                = new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword());

        return authenticationManagerBuilder.getObject().authenticate(authenticationToken);
    }
}
