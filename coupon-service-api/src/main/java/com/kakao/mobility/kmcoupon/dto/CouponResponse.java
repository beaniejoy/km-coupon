package com.kakao.mobility.kmcoupon.dto;

import java.math.BigDecimal;

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

    public Long getId() {
        return id;
    }

    public BigDecimal getUseMinAmount() {
        return useMinAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public String getUsableFrom() {
        return usableFrom;
    }

    public String getUsableUntil() {
        return usableUntil;
    }

    public String getStatus() {
        return status;
    }
}
