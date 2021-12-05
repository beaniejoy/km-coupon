package com.kakao.mobility.kmcoupon.convert;

import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mapstruct.factory.Mappers;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
class CouponDtoConverterTest {
    private static final String DATE_FORMAT = "yyyy-MM-dd";

    CouponDtoConverter couponDtoConvertor = Mappers.getMapper(CouponDtoConverter.class);

    List<Coupon> couponList;

    @BeforeEach
    public void setup() {
        Coupon coupon1 = Coupon.builder()
                .useMinAmount(BigDecimal.valueOf(5000))
                .discountAmount(BigDecimal.valueOf(5000))
                .usableFrom(LocalDateTime.of(2021, 2, 1, 0, 0, 0))
                .usableUntil(LocalDateTime.of(2021, 3, 1, 0, 0, 0))
                .build();

        Coupon coupon2 = Coupon.builder()
                .useMinAmount(BigDecimal.valueOf(7000))
                .discountAmount(BigDecimal.valueOf(10000))
                .usableFrom(LocalDateTime.of(2021, 1, 1, 0, 0, 0))
                .usableUntil(LocalDateTime.of(2021, 12, 1, 0, 0, 0))
                .build();

        couponList = new ArrayList<>();
        this.couponList.add(coupon1);
        this.couponList.add(coupon2);
    }

    @Test
    @DisplayName("couponResponse 한 개 데이터의 원하는 날짜 포맷 변경테스트")
    public void convertToDtoCouponTest() {
        Coupon coupon = couponList.get(0);

        CouponResponse couponResponse = couponDtoConvertor.of(coupon);

        String expected = coupon.getUsableFrom().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        assertEquals(expected, couponResponse.getUsableFrom());
    }

    @Test
    @DisplayName("couponResponse 리스트 데이터의 원하는 날짜 포맷 변경테스트")
    public void convertToCouponList() {
        List<CouponResponse> couponResponseList = couponDtoConvertor.of(couponList);
        Coupon coupon = couponList.get(0);
        String expected = coupon.getUsableFrom().format(DateTimeFormatter.ofPattern(DATE_FORMAT));

        assertEquals(expected, couponResponseList.get(0).getUsableFrom());
    }

}