package com.kakao.mobility.kmcoupon.domain;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
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

    @Builder
    public Coupon(
            BigDecimal useMinAmount,
            BigDecimal discountAmount,
            LocalDateTime usableFrom,
            LocalDateTime usableUntil) {

        this.useMinAmount = useMinAmount;
        this.discountAmount = discountAmount;
        this.usableFrom = usableFrom;
        this.usableUntil = usableUntil;

        this.status = Status.NORMAL;
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
