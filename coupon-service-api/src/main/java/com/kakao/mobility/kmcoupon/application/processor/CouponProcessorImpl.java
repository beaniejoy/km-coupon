package com.kakao.mobility.kmcoupon.application.processor;

import com.kakao.mobility.kmcoupon.application.validator.CouponValidator;
import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CouponProcessorImpl implements CouponProcessor {

    List<CouponValidator> couponValidatorList;

    public CouponProcessorImpl(List<CouponValidator> couponValidatorList) {
        this.couponValidatorList = couponValidatorList;
    }

    @Override
    public void useCoupon(Coupon coupon, CouponUsingRequest couponUsingRequest) {
        couponValidatorList.forEach(couponValidator -> couponValidator.validate(coupon, couponUsingRequest));
        coupon.used();
    }
}
