package com.kakao.mobility.kmcoupon.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
public class CouponUsingRequest {
    @NotNull(message = "쿠폰 ID는 필수 입력값입니다.")
    private Long couponId;

    @NotNull(message = "상품 가격은 필수 입력값입니다.")
    private BigDecimal itemAmount;

    // ???
    private LocalDateTime requestReceivedAt;

    public CouponUsingRequest(Long couponId, BigDecimal itemAmount) {
        this.couponId = couponId;
        this.itemAmount = itemAmount;
    }

    public void recordRequestReceivedTime(LocalDateTime requestReceivedAt) {
        this.requestReceivedAt = requestReceivedAt;
    }
}
