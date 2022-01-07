package com.kakao.mobility.kmcoupon.service

import com.kakao.mobility.kmcoupon.domain.coupon.Coupon
import com.kakao.mobility.kmcoupon.domain.coupon.Status
import com.kakao.mobility.kmcoupon.dto.CouponWithMemberDto
import com.kakao.mobility.kmcoupon.exception.CouponErrorMessage
import com.kakao.mobility.kmcoupon.exception.EntityNotFoundException
import com.kakao.mobility.kmcoupon.exception.InvalidRequestException
import com.kakao.mobility.kmcoupon.exception.InvalidStatusException
import com.kakao.mobility.kmcoupon.repository.CouponRepository
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.LocalDateTime

@Service
class CouponService(private val couponRepository: CouponRepository) {
    @Transactional(readOnly = true)
    // return 을 entity -> dto 로
    fun getUsableCouponList(memberId: Long, requestReceivedAt: LocalDateTime): List<Coupon> {
        return couponRepository.findAllUsableCoupon(
            memberId = memberId,
            // name x
            status = Status.NORMAL.name,
            // localdatetime공으로 (사용할거면 formatting)
            requestedAt = requestReceivedAt.toString()
        )
    }

    @Transactional
    fun useCoupon(
        memberId: Long,
        couponId: Long,
        itemAmount: BigDecimal,
        requestReceivedTime: LocalDateTime
    ): Coupon { // return dto 변환
        // nullable 로 ?: 사용해서
        val coupon = couponRepository.findByIdAndMemberId(couponId, memberId)
            .orElseThrow { EntityNotFoundException(CouponErrorMessage.COUPON_NOT_FOUND) }

        // 일관성 유지하는 것이 좋음 (fun 으로)
        if (coupon.isAlreadyUsed()) throw InvalidStatusException(CouponErrorMessage.COUPON_ALREADY_USED)
        if (coupon.isMinAmountBiggerThan(itemAmount)) throw InvalidRequestException(CouponErrorMessage.COUPON_MIN_AMOUNT_NOT_SATISFIED)
        if (coupon.isNotUsableDuration(requestReceivedTime)) throw InvalidRequestException(CouponErrorMessage.COUPON_NOT_USABLE_DURATION)

        coupon.use()

        return coupon
    }

    @Transactional(readOnly = true)
    fun getCouponListTest(
        memberId: Long,
        status: Status?
    ): List<Coupon> {
        return couponRepository.findAllByMemberIdAndStatus(memberId, status)
    }

    fun getCouponListMemberJoining(): List<CouponWithMemberDto> {
        return couponRepository.findAllMemberJoining()
    }
}