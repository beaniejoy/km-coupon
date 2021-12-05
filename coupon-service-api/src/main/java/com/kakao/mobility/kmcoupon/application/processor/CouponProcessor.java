package com.kakao.mobility.kmcoupon.application.processor;

import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;

public interface CouponProcessor {
    void useCoupon(Coupon coupon, CouponUsingRequest couponUsingRequest);
}
