package com.kakao.mobility.kmcoupon.repository

import com.kakao.mobility.kmcoupon.domain.coupon.Coupon
import com.kakao.mobility.kmcoupon.domain.coupon.Status
import com.kakao.mobility.kmcoupon.dto.CouponWithMemberDto
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.util.*

@Repository
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

    fun findAllByMemberIdAndStatus(memberId: Long, status: Status?): List<Coupon>

//    @Query("SELECT c FROM Coupon c JOIN FETCH Member m") 이런 방식은 에러 발생(c.member 이런 방식이어야 될듯)
    fun findAllMemberJoining(): List<CouponWithMemberDto>
}