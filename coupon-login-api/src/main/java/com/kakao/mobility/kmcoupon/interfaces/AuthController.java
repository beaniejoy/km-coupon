package com.kakao.mobility.kmcoupon.interfaces;

import com.kakao.mobility.kmcoupon.application.AuthService;
import com.kakao.mobility.kmcoupon.domain.member.Member;
import com.kakao.mobility.kmcoupon.dto.request.LoginRequest;
import com.kakao.mobility.kmcoupon.dto.response.TokenResponse;
import com.kakao.mobility.kmcoupon.util.JwtTokenUtil;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class AuthController {

    private final AuthService authService;
    private final JwtTokenUtil jwtTokenUtil;

    public AuthController(AuthService authService, JwtTokenUtil jwtTokenUtil) {
        this.authService = authService;
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @PostMapping("/api/v1/auth")
    public ResponseEntity<TokenResponse> signIn(@Valid @RequestBody LoginRequest loginRequest) {
        Authentication authenticatedMember = authService.authenticateMember(loginRequest);
        SecurityContextHolder.getContext().setAuthentication(authenticatedMember);
        String token = jwtTokenUtil.createToken(authenticatedMember);
        return ResponseEntity.ok(new TokenResponse(token));
    }
}
