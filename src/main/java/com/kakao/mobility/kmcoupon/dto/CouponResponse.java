package com.kakao.mobility.kmcoupon.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {
    private Long id;

    private BigDecimal useMinAmount;

    private BigDecimal discountAmount;

    private String usableFrom;

    private String usableUntil;

    private String status;
}
