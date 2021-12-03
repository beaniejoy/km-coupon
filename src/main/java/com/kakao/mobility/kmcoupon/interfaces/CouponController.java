package com.kakao.mobility.kmcoupon.interfaces;

import com.kakao.mobility.kmcoupon.application.CouponService;
import com.kakao.mobility.kmcoupon.domain.Coupon;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/coupon")
public class CouponController {

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/list")
    public ResponseEntity<List<Coupon>> usableCouponList() {

        List<Coupon> couponList = couponService.getUsableCouponList();

        return ResponseEntity.ok(couponList);
    }
}
