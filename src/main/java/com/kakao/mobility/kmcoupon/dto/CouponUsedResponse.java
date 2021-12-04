package com.kakao.mobility.kmcoupon.dto;

import com.kakao.mobility.kmcoupon.domain.Coupon;
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
