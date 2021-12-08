package com.kakao.mobility.kmcoupon.domain.member;

import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;

public class SecurityUser implements UserDetails {
    private Long memberId;

    private String username;
    private String password;
    private Collection<? extends GrantedAuthority> authorities;

    public SecurityUser() {
    }

    public SecurityUser(Long memberId, String username, String password, Collection<? extends GrantedAuthority> authorities) {
        this.memberId = memberId;
        this.username = username;
        this.password = password;
        this.authorities = authorities;
    }

    public Long getMemberId() {
        return memberId;
    }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return authorities;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
