package com.kakao.mobility.kmcoupon.util;

import com.kakao.mobility.kmcoupon.domain.member.SecurityUser;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Key;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

@Slf4j
public class JwtTokenUtil {

    private static final String AUTHORITIES_KEY = "authorities";
    private final Key key;
    private final long tokenValidInSeconds;

    public JwtTokenUtil(
            @Value("${jwt.secret}") String secret,
            @Value("${jwt.token-valid-in-seconds}") long tokenValidInSeconds) {
        this.key = Keys.hmacShaKeyFor(secret.getBytes());
        this.tokenValidInSeconds = tokenValidInSeconds;
    }

    public String createToken(Authentication authentication) {
        SecurityUser principal = (SecurityUser) authentication.getPrincipal();
        String authorities = authentication.getAuthorities()
                .stream()
                .map(GrantedAuthority::getAuthority)
                .collect(Collectors.joining(","));

        long now = new Date().getTime();
        Date expiredDate = new Date(now + this.tokenValidInSeconds * 1000);

        return Jwts.builder()
                .setSubject(principal.getUsername())
                .claim("memberId", principal.getMemberId())
                .claim(AUTHORITIES_KEY, authorities)
                .signWith(key, SignatureAlgorithm.HS256)
                .setExpiration(expiredDate)
                .compact();
    }

    public Authentication getAuthentication(String token) {
        Claims claims = Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();

        Collection<? extends GrantedAuthority> authorities =
                Arrays.stream(claims.get(AUTHORITIES_KEY).toString().split(","))
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());

        SecurityUser principal = new SecurityUser(
                claims.get("memberId", Long.class),
                claims.getSubject(),
                null,
                authorities);

        return new UsernamePasswordAuthenticationToken(principal, token, authorities);
    }

    public boolean validateJwtToken(String token) {
        try {
            Jwts.parserBuilder().setSigningKey(key).build().parseClaimsJws(token);
            return true;
        } catch (RuntimeException e) {
            log.info("################ 유효하지 않은 JWT 토큰입니다. 다시 발급해주세요. ################");
        }
        return false;
    }
}
