package com.kakao.mobility.kmcoupon.domain;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

class CouponAmountTest {

    @Test
    public void compareAmount() {
        BigDecimal amount1 = BigDecimal.valueOf(10L);
        BigDecimal amount2 = BigDecimal.valueOf(10L);

        System.out.println(LocalDateTime.now());
        assertEquals(amount1.compareTo(amount2) == 0, true);
    }
}