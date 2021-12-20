package com.kakao.mobility.kmcoupon.repository

import com.kakao.mobility.kmcoupon.domain.coupon.Coupon
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import java.util.*

interface CouponRepository : JpaRepository<Coupon, Long> {
    @Query(
        value = "SELECT * " +
                "FROM coupon " +
                "WHERE member_id = :memberId " +
                "AND status = :status " +
                "AND usable_from <= str_to_date(:requestedAt, '%Y-%m-%dT%H:%i:%s') " +
                "AND usable_until >= str_to_date(:requestedAt, '%Y-%m-%dT%H:%i:%s') " +
                "ORDER BY usable_until ASC, discount_amount DESC", nativeQuery = true
    )
    // query method (native query x)
    fun findAllUsableCoupon(
        @Param("memberId") memberId: Long,
        @Param("status") status: String,
        @Param("requestedAt") requestedAt: String
    ): List<Coupon>

    fun findByIdAndMemberId(id: Long, memberId: Long): Optional<Coupon>
}