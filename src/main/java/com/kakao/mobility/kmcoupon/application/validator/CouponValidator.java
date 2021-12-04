package com.kakao.mobility.kmcoupon.application.validator;

import com.kakao.mobility.kmcoupon.domain.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;

public interface CouponValidator {
    void validate(Coupon coupon, CouponUsingRequest couponUsingRequest);
}
