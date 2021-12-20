package com.kakao.mobility.kmcoupon.controller

import com.kakao.mobility.kmcoupon.service.CouponService
import com.kakao.mobility.kmcoupon.domain.coupon.Coupon
import com.kakao.mobility.kmcoupon.domain.member.SecurityUser
import com.kakao.mobility.kmcoupon.dto.CouponResponse
import com.kakao.mobility.kmcoupon.dto.CouponUsedResponse
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.*
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/coupons")
@Api(tags = ["쿠폰 API"])
class CouponController(
    private val couponService: CouponService
) {
    companion object {
        private const val DATE_FORMAT = "yyyy-MM-dd HH:mm:ss"
    }

    @GetMapping("/usable")
    @ApiOperation(value = "사용가능 쿠폰 조회")
    fun listUsableCoupons(): List<CouponResponse> {
        val couponList = couponService.getUsableCouponList(
            memberId = getSecurityUser().memberId,
            requestReceivedAt = LocalDateTime.now()
        )

        return couponList.map { toCouponResponse(it) }
    }

    @PutMapping("/{couponId}/use")
    @ApiOperation(value = "쿠폰 사용")
    fun useCoupon(
        @PathVariable("couponId") couponId: Long,
        @Valid @RequestBody couponUsingRequest: CouponUsingRequest
    ): CouponUsedResponse {
        val itemAmount = couponUsingRequest.itemAmount
        // 계산을 service 단계에서 완료되도록
        val usedCoupon: Coupon = couponService.useCoupon(
            // 항상 Null check
            memberId = getSecurityUser().memberId,
            couponId = couponId,
            itemAmount = itemAmount,
            requestReceivedTime = LocalDateTime.now()
        )

        return CouponUsedResponse(
            // entity 가져다 사용안할거기때문에 변경해야함
            itemAmount = itemAmount,
            payAmount = usedCoupon.calPayAmount(itemAmount),
            actualDiscountAmount = usedCoupon.calActualDiscountAmount(itemAmount)
        )
    }

    private fun getSecurityUser(): SecurityUser {
        return SecurityContextHolder.getContext().authentication.principal as SecurityUser
    }

    private fun toCouponResponse(coupon: Coupon): CouponResponse {
        return CouponResponse(
            id = coupon.id,
            useMinAmount = coupon.useMinAmount,
            discountAmount = coupon.discountAmount,
            usableFrom = coupon.usableFrom.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
            usableUntil = coupon.usableUntil.format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
            status = coupon.status.name
        )
    }
}