package com.kakao.mobility.kmcoupon.application.validator;

import com.kakao.mobility.kmcoupon.common.exception.InvalidStatusException;
import com.kakao.mobility.kmcoupon.domain.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Order(value = 1)
@Component
public class CouponStatusValidator implements CouponValidator{
    @Override
    public void validate(Coupon coupon, CouponUsingRequest couponUsingRequest) {
        if(coupon.isAlreadyUsed())
            throw new InvalidStatusException("이미 사용된 쿠폰입니다.");
    }
}
