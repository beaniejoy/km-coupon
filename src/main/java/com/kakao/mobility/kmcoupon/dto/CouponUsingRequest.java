package com.kakao.mobility.kmcoupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponUsingRequest {
    @NotNull(message = "쿠폰 ID는 필수 입력값입니다.")
    private Long couponId;

    @NotNull(message = "상품 가격은 필수 입력값입니다.")
    private BigDecimal itemAmount;
}
