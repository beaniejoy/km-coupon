package com.kakao.mobility.kmcoupon.dto;

import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CouponResponse {
    private Long id;

    private BigDecimal useMinAmount;

    private BigDecimal discountAmount;

    private String usableFrom;

    private String usableUntil;

    private String status;

    public CouponResponse(Long id, BigDecimal useMinAmount, BigDecimal discountAmount, String usableFrom, String usableUntil, String status) {
        this.id = id;
        this.useMinAmount = useMinAmount;
        this.discountAmount = discountAmount;
        this.usableFrom = usableFrom;
        this.usableUntil = usableUntil;
        this.status = status;
    }
}
