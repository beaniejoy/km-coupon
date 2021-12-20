package com.kakao.mobility.kmcoupon.domain.member

import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.userdetails.UserDetails

// java 변환시 1개 constructor 생성
// kotlin-jpa 플러그인에 의해 entity 관련 클래스에 대해서 default 제공
class SecurityUser(
    val memberId: Long,
    private val username: String,
    private val password: String,
    private val authorities: Collection<GrantedAuthority> // 다른 방식으로
) : UserDetails {
    // never null 이기에 확정형으로
    override fun getUsername(): String {
        return username
    }

    override fun getPassword(): String {
        return password
    }

    override fun getAuthorities(): Collection<GrantedAuthority> {
        return authorities
    }

    override fun isAccountNonExpired(): Boolean {
        return true
    }

    override fun isAccountNonLocked(): Boolean {
        return true
    }

    override fun isCredentialsNonExpired(): Boolean {
        return true
    }

    override fun isEnabled(): Boolean {
        return true
    }
}