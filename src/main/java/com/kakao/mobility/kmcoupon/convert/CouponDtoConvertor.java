package com.kakao.mobility.kmcoupon.convert;

import com.kakao.mobility.kmcoupon.domain.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CouponDtoConvertor {
    @Mapping(source = "usableFrom", target = "usableFrom", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(source = "usableUntil", target = "usableUntil", dateFormat = "yyyy-MM-dd HH:mm:ss")
    @Mapping(expression = "java(coupon.getStatus().name())", target = "status")
    CouponResponse of(Coupon coupon);

    List<CouponResponse> of(List<Coupon> couponList);
}
