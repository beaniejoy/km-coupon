package com.kakao.mobility.kmcoupon.convert;

import com.kakao.mobility.kmcoupon.domain.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponResponse;
import org.mapstruct.*;

import java.util.List;
import java.util.stream.Collectors;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface CouponDtoConvertor {
    @Mappings({
            @Mapping(source = "usableFrom", target = "usableFrom", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(source = "usableUntil", target = "usableUntil", dateFormat = "yyyy-MM-dd HH:mm:ss"),
            @Mapping(expression = "java(coupon.getStatus().name())", target = "status")
    })
    CouponResponse of(Coupon coupon);
    
    default List<CouponResponse> of(List<Coupon> couponList) {
        return couponList.stream()
                .map(this::of)
                .collect(Collectors.toList());
    }
}
