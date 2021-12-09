package com.kakao.mobility.kmcoupon.dto;

import java.math.BigDecimal;

public class CouponUsedResponse {
    private BigDecimal itemAmount;

    private BigDecimal payAmount;

    private BigDecimal actualDiscountAmount;

    public CouponUsedResponse(BigDecimal itemAmount, BigDecimal payAmount, BigDecimal actualDiscountAmount) {
        this.itemAmount = itemAmount;
        this.payAmount = payAmount;
        this.actualDiscountAmount = actualDiscountAmount;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public BigDecimal getPayAmount() {
        return payAmount;
    }

    public BigDecimal getActualDiscountAmount() {
        return actualDiscountAmount;
    }
}
