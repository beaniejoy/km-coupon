package com.kakao.mobility.kmcoupon.application;

import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.domain.coupon.Status;
import com.kakao.mobility.kmcoupon.exception.CouponErrorMessage;
import com.kakao.mobility.kmcoupon.exception.EntityNotFoundException;
import com.kakao.mobility.kmcoupon.exception.InvalidRequestException;
import com.kakao.mobility.kmcoupon.exception.InvalidStatusException;
import com.kakao.mobility.kmcoupon.repository.CouponRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
public class CouponService {

    private final CouponRepository couponRepository;

    public CouponService(CouponRepository couponRepository) {
        this.couponRepository = couponRepository;
    }

    @Transactional(readOnly = true)
    public List<Coupon> getUsableCouponList(Long memberId, LocalDateTime requestReceivedAt) {
        return couponRepository.findAllUsableCoupon(memberId, Status.NORMAL.name(), requestReceivedAt.toString());
    }

    @Transactional
    public Coupon useCoupon(Long memberId, Long couponId, BigDecimal itemAmount, LocalDateTime requestReceivedTime) {
        Coupon coupon = couponRepository.findByIdAndMemberId(couponId, memberId)
                .orElseThrow(() -> new EntityNotFoundException(CouponErrorMessage.COUPON_NOT_FOUND));

        if (coupon.isAlreadyUsed())
            throw new InvalidStatusException(CouponErrorMessage.COUPON_ALREADY_USED);

        if (coupon.isMinAmountBiggerThan(itemAmount))
            throw new InvalidRequestException(CouponErrorMessage.COUPON_MIN_AMOUNT_NOT_SATISFIED);

        if (coupon.isNotUsableDuration(requestReceivedTime))
            throw new InvalidRequestException(CouponErrorMessage.COUPON_NOT_USABLE_DURATION);

        coupon.used();

        return coupon;
    }
}
