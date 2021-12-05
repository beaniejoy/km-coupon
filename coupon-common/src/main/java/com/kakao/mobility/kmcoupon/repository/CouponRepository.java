package com.kakao.mobility.kmcoupon.repository;

import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query(value =
            "SELECT * " +
            "FROM coupon " +
            "WHERE member_id = :memberId " +
            "AND status = :status " +
            "AND usable_from <= str_to_date(:requestedAt, '%Y-%m-%dT%H:%i:%s') " +
            "AND usable_until >= str_to_date(:requestedAt, '%Y-%m-%dT%H:%i:%s') " +
            "ORDER BY usable_until ASC, discount_amount DESC",
            nativeQuery = true)
    List<Coupon> findAllUsableCoupon(@Param("memberId") Long memberId, @Param("status") String status, @Param("requestedAt") String requestedAt);

    Optional<Coupon> findByIdAndMemberId(Long id, Long memberId);
}