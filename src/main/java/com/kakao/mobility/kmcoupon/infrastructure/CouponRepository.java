package com.kakao.mobility.kmcoupon.infrastructure;

import com.kakao.mobility.kmcoupon.domain.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
}
