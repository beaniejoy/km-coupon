package com.kakao.mobility.kmcoupon.application.validator;

import com.kakao.mobility.kmcoupon.exception.CouponErrorMessage;
import com.kakao.mobility.kmcoupon.exception.InvalidRequestException;
import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Order(value = 2)
@Component
public class CouponMinAmountValidator implements CouponValidator {
    @Override
    public void validate(Coupon coupon, CouponUsingRequest couponUsingRequest) {
        BigDecimal itemAmount = couponUsingRequest.getItemAmount();
        if (coupon.isMinAmountBiggerThan(itemAmount))
            throw new InvalidRequestException(CouponErrorMessage.COUPON_MIN_AMOUNT_NOT_SATISFIED);
    }
}
