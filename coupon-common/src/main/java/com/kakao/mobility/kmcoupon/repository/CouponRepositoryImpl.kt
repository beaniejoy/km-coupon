package com.kakao.mobility.kmcoupon.repository

import com.kakao.mobility.kmcoupon.dto.CouponWithMemberDto
import org.springframework.data.jpa.repository.Query
import javax.persistence.EntityManagerFactory

class CouponRepositoryImpl(
    emf: EntityManagerFactory
): CouponRepositoryCustom{

    override fun findAllMemberJoining(): List<CouponWithMemberDto> {
        TODO("Not yet implemented")
    }
}