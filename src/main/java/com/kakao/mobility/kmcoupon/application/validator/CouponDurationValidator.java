package com.kakao.mobility.kmcoupon.application.validator;

import com.kakao.mobility.kmcoupon.common.exception.CouponErrorMessage;
import com.kakao.mobility.kmcoupon.common.exception.InvalidRequestException;
import com.kakao.mobility.kmcoupon.domain.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Order(value = 3)
@Component
public class CouponDurationValidator implements CouponValidator {
    @Override
    public void validate(Coupon coupon, CouponUsingRequest couponUsingRequest) {
        LocalDateTime requestReceivedTime = couponUsingRequest.getRequestReceivedAt();
        if (coupon.isNotUsableDuration(requestReceivedTime))
            throw new InvalidRequestException(CouponErrorMessage.COUPON_NOT_USABLE_DURATION);
    }
}
