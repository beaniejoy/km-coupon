package com.kakao.mobility.kmcoupon.dto;

import com.kakao.mobility.kmcoupon.domain.Status;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CouponResponse {
    private Long id;

    private BigDecimal useMinAmount;

    private BigDecimal discountAmount;

    private LocalDateTime usableFrom;

    private LocalDateTime usableUntil;

    private String status;
}
