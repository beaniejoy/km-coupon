package com.kakao.mobility.kmcoupon.interfaces;

import com.kakao.mobility.kmcoupon.annotation.TimeRecord;
import com.kakao.mobility.kmcoupon.application.CouponService;
import com.kakao.mobility.kmcoupon.convert.CouponDtoConverter;
import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.dto.CouponResponse;
import com.kakao.mobility.kmcoupon.dto.CouponUsedResponse;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

    private final CouponService couponService;
    private final CouponDtoConverter couponDtoConvertor;

    public CouponController(CouponService couponService, CouponDtoConverter couponDtoConvertor) {
        this.couponService = couponService;
        this.couponDtoConvertor = couponDtoConvertor;
    }

    @GetMapping("/usable")
    public ResponseEntity<List<CouponResponse>> listUsableCoupons(
            @TimeRecord LocalDateTime requestReceivedAt
    ) {
        List<Coupon> couponList = couponService.getUsableCouponList(requestReceivedAt);
        List<CouponResponse> couponResponseList = couponDtoConvertor.of(couponList);
        return ResponseEntity.ok(couponResponseList);
    }

    @PostMapping("/use")
    public ResponseEntity<CouponUsedResponse> useCoupon(
            @Valid @RequestBody CouponUsingRequest couponUsingRequest,
            @TimeRecord LocalDateTime requestReceivedAt
    ) {
        couponUsingRequest.recordRequestReceivedTime(requestReceivedAt);
        CouponUsedResponse couponUsedResponse = couponService.useCoupon(couponUsingRequest);
        return ResponseEntity.ok(couponUsedResponse);
    }
}
