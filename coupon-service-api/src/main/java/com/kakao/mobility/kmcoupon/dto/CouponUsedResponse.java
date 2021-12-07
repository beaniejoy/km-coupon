package com.kakao.mobility.kmcoupon.dto;

import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import lombok.Getter;

import java.math.BigDecimal;

@Getter
public class CouponUsedResponse {
    private BigDecimal itemAmount;

    private BigDecimal payAmount;

    private BigDecimal actualDiscountAmount;

    public CouponUsedResponse(BigDecimal itemAmount, BigDecimal payAmount, BigDecimal actualDiscountAmount) {
        this.itemAmount = itemAmount;
        this.payAmount = payAmount;
        this.actualDiscountAmount = actualDiscountAmount;
    }

    public static CouponUsedResponse makeUsedResponse(Coupon coupon, CouponUsingRequest request) {
        BigDecimal itemAmount = request.getItemAmount();
        BigDecimal discountAmount = coupon.getDiscountAmount();

        BigDecimal payAmount = null;
        BigDecimal actualDiscountAmount = null;

        if (itemAmount.compareTo(discountAmount) <= 0) {
            payAmount = BigDecimal.ZERO;
            actualDiscountAmount = itemAmount;
        } else {
            payAmount = itemAmount.subtract(discountAmount);
            actualDiscountAmount = discountAmount;
        }

        return new CouponUsedResponse(itemAmount, payAmount, actualDiscountAmount);
    }
}
