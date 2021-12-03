package com.kakao.mobility.kmcoupon.infrastructure;

import com.kakao.mobility.kmcoupon.domain.Coupon;
import com.kakao.mobility.kmcoupon.domain.Status;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    List<Coupon> findAllByStatusOrderByUsableUntilAscDiscountAmountDesc(Status status);
}
