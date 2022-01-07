package com.kakao.mobility.kmcoupon.repository

import com.kakao.mobility.kmcoupon.dto.CouponWithMemberDto

interface CouponRepositoryCustom {
    fun findAllMemberJoining(): List<CouponWithMemberDto>
}