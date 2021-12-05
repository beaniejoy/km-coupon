package com.kakao.mobility.kmcoupon.common.filter;

import com.kakao.mobility.kmcoupon.domain.member.SecurityUser;
import com.kakao.mobility.kmcoupon.util.JwtTokenUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@Slf4j
public class JwtAuthenticationFilter extends GenericFilterBean {
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final int BEARER_PREFIX_COUNT = 7;

    private final JwtTokenUtil jwtTokenUtil;

    public JwtAuthenticationFilter(JwtTokenUtil jwtTokenUtil) {
        this.jwtTokenUtil = jwtTokenUtil;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = extractToken(httpServletRequest);

        if (StringUtils.hasText(token) && jwtTokenUtil.validateJwtToken(token)) {
            Authentication authentication = jwtTokenUtil.getAuthentication(token);
            SecurityContextHolder.getContext().setAuthentication(authentication);
            log.info(((SecurityUser)authentication.getPrincipal()).getUsername());
            authentication.getAuthorities()
                    .forEach(grantedAuthority -> log.info(grantedAuthority.getAuthority()));
        }

        chain.doFilter(request, response);
    }

    private String extractToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(AUTHORIZATION_HEADER);
        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(BEARER_PREFIX_COUNT);
        }
        return null;
    }
}
