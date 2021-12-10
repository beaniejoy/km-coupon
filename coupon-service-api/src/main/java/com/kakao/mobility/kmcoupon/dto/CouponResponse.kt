package com.kakao.mobility.kmcoupon.dto

import java.math.BigDecimal

data class CouponResponse(
    val id: Long,
    val useMinAmount: BigDecimal,
    val discountAmount: BigDecimal,
    val usableFrom: String,
    val usableUntil: String,
    val status: String
)