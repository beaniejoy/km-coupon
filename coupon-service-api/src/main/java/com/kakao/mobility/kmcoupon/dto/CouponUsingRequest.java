package com.kakao.mobility.kmcoupon.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponUsingRequest {
    @NotNull(message = "쿠폰 ID는 필수 입력값입니다.")
    private Long couponId;

    @NotNull(message = "상품 가격은 필수 입력값입니다.")
    private BigDecimal itemAmount;

    private LocalDateTime requestReceivedAt;

    public CouponUsingRequest(Long couponId, BigDecimal itemAmount) {
        this.couponId = couponId;
        this.itemAmount = itemAmount;
        this.requestReceivedAt = LocalDateTime.now();
    }

    public Long getCouponId() {
        return couponId;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }

    public LocalDateTime getRequestReceivedAt() {
        return requestReceivedAt;
    }
}
