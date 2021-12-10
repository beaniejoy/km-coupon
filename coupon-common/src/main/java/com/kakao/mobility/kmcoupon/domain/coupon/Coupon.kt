package com.kakao.mobility.kmcoupon.domain.coupon

import com.kakao.mobility.kmcoupon.domain.BaseTimeEntity
import java.math.BigDecimal
import java.time.LocalDateTime
import javax.persistence.*

@Entity
@Table(name = "coupon")
class Coupon(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "use_min_amount")
    val useMinAmount: BigDecimal,

    @Column(name = "discount_amount")
    val discountAmount: BigDecimal,

    @Column(name = "usable_from")
    val usableFrom: LocalDateTime,

    @Column(name = "usable_until")
    val usableUntil: LocalDateTime,

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    var status: Status = Status.NORMAL,

    @Column(name = "member_id")
    var memberId: Long = 0L
) : BaseTimeEntity() {
    val isAlreadyUsed: Boolean
        get() = status == Status.USED

    fun used() {
        status = Status.USED
    }

    fun isMinAmountBiggerThan(itemAmount: BigDecimal): Boolean {
        return useMinAmount > itemAmount
    }

    fun isNotUsableDuration(requestReceivedTime: LocalDateTime): Boolean {
        return (usableFrom.isAfter(requestReceivedTime)
                || usableUntil.isBefore(requestReceivedTime))
    }

    fun calPayAmount(itemAmount: BigDecimal): BigDecimal {
        return if (itemAmount <= discountAmount) {
            BigDecimal.ZERO
        } else {
            itemAmount.subtract(discountAmount)
        }
    }

    fun calActualDiscountAmount(itemAmount: BigDecimal): BigDecimal {
        return if (itemAmount <= discountAmount) {
            itemAmount
        } else {
            discountAmount
        }
    }
}