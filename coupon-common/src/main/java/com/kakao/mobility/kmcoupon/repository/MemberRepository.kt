package com.kakao.mobility.kmcoupon.repository

import com.kakao.mobility.kmcoupon.domain.member.Member
import org.springframework.data.jpa.repository.JpaRepository
import java.util.*

interface MemberRepository : JpaRepository<Member, Long> {
    fun findByEmail(email: String): Optional<Member>
}