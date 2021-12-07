package com.kakao.mobility.kmcoupon.application;

import com.kakao.mobility.kmcoupon.application.processor.CouponProcessor;
import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.domain.coupon.Status;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import com.kakao.mobility.kmcoupon.exception.CouponErrorMessage;
import com.kakao.mobility.kmcoupon.exception.EntityNotFoundException;
import com.kakao.mobility.kmcoupon.repository.CouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CouponService {

    private final CouponRepository couponRepository;
    private final CouponProcessor couponProcessor;

    public CouponService(CouponRepository couponRepository, CouponProcessor couponProcessor) {
        this.couponRepository = couponRepository;
        this.couponProcessor = couponProcessor;
    }

    @Transactional(readOnly = true)
    public List<Coupon> getUsableCouponList(Long memberId, LocalDateTime requestReceivedAt) {
        return couponRepository.findAllUsableCoupon(memberId, Status.NORMAL.toString(), requestReceivedAt.toString());
    }

    @Transactional
    public Coupon useCoupon(Long memberId, CouponUsingRequest couponUsingRequest) {
        Long couponId = couponUsingRequest.getCouponId();
        Coupon coupon = couponRepository.findByIdAndMemberId(couponId, memberId)
                .orElseThrow(() -> new EntityNotFoundException(CouponErrorMessage.COUPON_NOT_FOUND));
        couponProcessor.useCoupon(coupon, couponUsingRequest);

        return coupon;
    }
}
