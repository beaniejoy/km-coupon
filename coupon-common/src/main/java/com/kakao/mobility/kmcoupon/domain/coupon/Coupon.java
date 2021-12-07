package com.kakao.mobility.kmcoupon.domain.coupon;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "coupon")
public class Coupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private BigDecimal useMinAmount;

    private BigDecimal discountAmount;

    private LocalDateTime usableFrom;

    private LocalDateTime usableUntil;

    @Enumerated(EnumType.STRING)
    private Status status;

    private Long memberId;

    public Coupon() {
    }

    public Coupon(
            BigDecimal useMinAmount,
            BigDecimal discountAmount,
            LocalDateTime usableFrom,
            LocalDateTime usableUntil,
            Long memberId) {
        this.useMinAmount = useMinAmount;
        this.discountAmount = discountAmount;
        this.usableFrom = usableFrom;
        this.usableUntil = usableUntil;
        this.memberId = memberId;

        this.status = Status.NORMAL;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getUseMinAmount() {
        return useMinAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public LocalDateTime getUsableFrom() {
        return usableFrom;
    }

    public LocalDateTime getUsableUntil() {
        return usableUntil;
    }

    public Status getStatus() {
        return status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void used() {
        this.status = Status.USED;
    }

    public boolean isAlreadyUsed() {
        return this.status == Status.USED;
    }

    public boolean isMinAmountBiggerThan(BigDecimal itemAmount) {
        return this.useMinAmount.compareTo(itemAmount) > 0;
    }

    public boolean isNotUsableDuration(LocalDateTime requestReceivedTime) {
        return this.usableFrom.isAfter(requestReceivedTime)
                || this.usableUntil.isBefore(requestReceivedTime);
    }
}
