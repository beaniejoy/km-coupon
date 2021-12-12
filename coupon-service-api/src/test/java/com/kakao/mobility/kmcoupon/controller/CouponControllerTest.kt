package com.kakao.mobility.kmcoupon.controller

import com.kakao.mobility.kmcoupon.service.CouponService
import com.kakao.mobility.kmcoupon.domain.coupon.Coupon
import com.kakao.mobility.kmcoupon.dto.CouponResponse
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.test.context.ActiveProfiles
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter

@SpringBootTest(
    webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT
)
@Transactional
@ActiveProfiles("test")
internal class CouponControllerTest @Autowired constructor(
    val couponService: CouponService
) {
    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    @Test
    fun makeCouponListTest() {
        val memberId = 100L

        val couponList = couponService.getUsableCouponList(memberId, LocalDateTime.now())
        couponList.map { toCouponResponse(it) }
    }

    @Test
    fun hello() {
        println("hello!")
    }

    private fun toCouponResponse(coupon: Coupon): CouponResponse {
        return CouponResponse(
            coupon.id,
            coupon.useMinAmount,
            coupon.discountAmount,
            coupon.usableFrom.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
            coupon.usableUntil.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
            coupon.status.name
        )
    }
}