package com.kakao.mobility.kmcoupon.interfaces;

import com.kakao.mobility.kmcoupon.application.CouponService;
import com.kakao.mobility.kmcoupon.common.annotation.TimeRecord;
import com.kakao.mobility.kmcoupon.convert.CouponDtoConvertor;
import com.kakao.mobility.kmcoupon.domain.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponResponse;
import com.kakao.mobility.kmcoupon.dto.CouponUsedResponse;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

    private final CouponService couponService;
    private final CouponDtoConvertor couponDtoConvertor;

    public CouponController(CouponService couponService, CouponDtoConvertor couponDtoConvertor) {
        this.couponService = couponService;
        this.couponDtoConvertor = couponDtoConvertor;
    }

    @GetMapping("/usable")
    public ResponseEntity<List<CouponResponse>> listUsableCoupons() {
        List<Coupon> couponList = couponService.getUsableCouponList();
        List<CouponResponse> couponResponseList = couponDtoConvertor.of(couponList);
        return ResponseEntity.ok(couponResponseList);
    }

    @PatchMapping("/use")
    public ResponseEntity<CouponUsedResponse> useCoupon(
            @RequestBody CouponUsingRequest couponUsingRequest,
            @TimeRecord LocalDateTime requestReceivedAt
    ) {
        couponUsingRequest.recordRequestReceivedTime(requestReceivedAt);
        couponService.useCoupon(couponUsingRequest);
        return ResponseEntity.ok(null);
    }
}
