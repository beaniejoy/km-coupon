package com.kakao.mobility.kmcoupon.application;

import com.kakao.mobility.kmcoupon.application.processor.CouponProcessor;
import com.kakao.mobility.kmcoupon.common.exception.EntityNotFoundException;
import com.kakao.mobility.kmcoupon.domain.Coupon;
import com.kakao.mobility.kmcoupon.domain.Status;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import com.kakao.mobility.kmcoupon.infrastructure.CouponRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponProcessor couponProcessor;

    public CouponService(CouponRepository couponRepository, CouponProcessor couponProcessor) {
        this.couponRepository = couponRepository;
        this.couponProcessor = couponProcessor;
    }

    @Transactional(readOnly = true)
    public List<Coupon> getUsableCouponList() {

        return couponRepository.findAllByStatusOrderByUsableUntilAscDiscountAmountDesc(Status.NORMAL);
    }

    @Transactional
    public void useCoupon(CouponUsingRequest couponUsingRequest) {
        // 1. coupon id 기준으로 쿠폰 조회 -> 없으면 Exception
        Long couponId = couponUsingRequest.getCouponId();
        Coupon coupon = couponRepository.findById(couponId)
                .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 쿠폰입니다."));
        // 2. 사용가능 쿠폰인지 체크
        // - a. USED가 아닌 status 값
        // - b. 최소 사용 가능 금액 ≤ 상품가격
        // - c. 사용가능일시, 만료되지 않은 쿠폰
        // - d. 위 케이스 중 하나라도 만족 못하면 Exception 처리
        // 3. 사용가능 쿠폰인 경우 해당 Status를 "USED"로 업데이트
        couponProcessor.useCoupon(coupon, couponUsingRequest);

        // 4. 사용처리 응답 반환할 것
        // - a. 상품금액
        // - b. 결제금액(할인하고 남은 실제 결제금액)
        // - c. 실제 할인 금액(쿠폰 할인금액이 아닌 실제 결제때 사용된 할인금액)
    }
}
