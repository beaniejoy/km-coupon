package com.kakao.mobility.kmcoupon.exception;

public class CouponErrorMessage {
    public static final String COUPON_NOT_FOUND = "존재하지 않는 쿠폰입니다.";
    public static final String COUPON_ALREADY_USED = "이미 사용된 쿠폰입니다.";
    public static final String COUPON_MIN_AMOUNT_NOT_SATISFIED = "해당 쿠폰의 최소 사용 금액보다 상품 금액이 작습니다.";
    public static final String COUPON_NOT_USABLE_DURATION = "사용 가능기간에 해당하지 않거나 만료된 쿠폰입니다.";
}
