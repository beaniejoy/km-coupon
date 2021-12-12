package com.kakao.mobility.kmcoupon.service

import com.kakao.mobility.kmcoupon.domain.member.Member
import com.kakao.mobility.kmcoupon.domain.member.SecurityUser
import com.kakao.mobility.kmcoupon.exception.InvalidRequestException
import com.kakao.mobility.kmcoupon.repository.MemberRepository
import org.springframework.security.core.GrantedAuthority
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.security.core.userdetails.UsernameNotFoundException
import org.springframework.stereotype.Component
import javax.transaction.Transactional

@Component("userDetailsService")
class UserDetailsServiceImpl(private val memberRepository: MemberRepository) : UserDetailsService {
    @Transactional
    @Throws(UsernameNotFoundException::class)
    override fun loadUserByUsername(email: String): SecurityUser {
        return memberRepository.findByEmail(email)
            .map { member: Member -> createSecurityUser(member) }
            .orElseThrow { InvalidRequestException("이메일 계정이 존재하지 않습니다.") }
    }

    private fun createSecurityUser(member: Member): SecurityUser {
        val grantedAuthority: GrantedAuthority = SimpleGrantedAuthority(member.role.name)
        val grantedAuthorityList = listOf(grantedAuthority)
        return SecurityUser(member.id, member.email, member.password, grantedAuthorityList)
    }
}