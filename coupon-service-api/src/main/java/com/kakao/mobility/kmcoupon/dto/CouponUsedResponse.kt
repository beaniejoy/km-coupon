package com.kakao.mobility.kmcoupon.dto

import java.math.BigDecimal

data class CouponUsedResponse(
    val itemAmount: BigDecimal,
    val payAmount: BigDecimal,
    val actualDiscountAmount: BigDecimal
)