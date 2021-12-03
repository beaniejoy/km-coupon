package com.kakao.mobility.kmcoupon.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CouponUsedResponse {
    private BigDecimal itemAmount;

    private BigDecimal payAmount;

    private BigDecimal actualDiscountAmount;
}
