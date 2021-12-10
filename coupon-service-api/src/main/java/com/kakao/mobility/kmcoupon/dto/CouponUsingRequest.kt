package com.kakao.mobility.kmcoupon.dto

import java.math.BigDecimal
import javax.validation.constraints.NotNull

data class CouponUsingRequest(
    @NotNull(message = "상품 가격은 필수 입력값입니다.")
    val itemAmount: BigDecimal
)