package com.kakao.mobility.kmcoupon.service

import com.kakao.mobility.kmcoupon.domain.member.Member
import com.kakao.mobility.kmcoupon.dto.request.MemberRegistrationRequest
import com.kakao.mobility.kmcoupon.exception.InvalidRequestException
import com.kakao.mobility.kmcoupon.repository.MemberRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class MemberService(
    private val memberRepository: MemberRepository,
    private val passwordEncoder: PasswordEncoder
) {
    @Transactional
    fun registerMember(registrationRequest: MemberRegistrationRequest): Member {
        val email = registrationRequest.email
        val foundMember = memberRepository.findByEmail(email)
        foundMember.ifPresent {
            throw InvalidRequestException("해당 이메일[${email}]은 이미 등록된 계정입니다.") }


        return memberRepository.save(createMember(registrationRequest))
    }

    private fun createMember(registrationRequest: MemberRegistrationRequest): Member {
        return Member(
            email = registrationRequest.email,
            password = passwordEncoder.encode(registrationRequest.password)
        )
    }
}