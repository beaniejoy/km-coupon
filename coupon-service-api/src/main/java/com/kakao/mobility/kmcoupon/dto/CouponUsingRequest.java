package com.kakao.mobility.kmcoupon.dto;

import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class CouponUsingRequest {
    @NotNull(message = "상품 가격은 필수 입력값입니다.")
    private BigDecimal itemAmount;

    public CouponUsingRequest() {
    }

    public CouponUsingRequest(BigDecimal itemAmount) {
        this.itemAmount = itemAmount;
    }

    public BigDecimal getItemAmount() {
        return itemAmount;
    }
}
