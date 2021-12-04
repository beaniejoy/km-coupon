package com.kakao.mobility.kmcoupon.application.validator;

import com.kakao.mobility.kmcoupon.common.exception.InvalidRequestException;
import com.kakao.mobility.kmcoupon.domain.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Order(value = 2)
@Component
public class CouponMinAmountValidator implements CouponValidator{
    @Override
    public void validate(Coupon coupon, CouponUsingRequest couponUsingRequest) {
        BigDecimal itemAmount = couponUsingRequest.getItemAmount();
        if(coupon.isMinAmountBiggerThan(itemAmount))
            throw new InvalidRequestException("해당 쿠폰의 최소 사용 금액보다 상품 금액이 작습니다.");
    }
}
