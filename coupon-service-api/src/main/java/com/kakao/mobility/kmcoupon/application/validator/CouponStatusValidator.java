package com.kakao.mobility.kmcoupon.application.validator;

import com.kakao.mobility.kmcoupon.exception.CouponErrorMessage;
import com.kakao.mobility.kmcoupon.exception.InvalidStatusException;
import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 1)
@Component
public class CouponStatusValidator implements CouponValidator {
    @Override
    public void validate(Coupon coupon, CouponUsingRequest couponUsingRequest) {
        if (coupon.isAlreadyUsed())
            throw new InvalidStatusException(CouponErrorMessage.COUPON_ALREADY_USED);
    }
}
