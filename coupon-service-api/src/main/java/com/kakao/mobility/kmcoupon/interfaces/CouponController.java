package com.kakao.mobility.kmcoupon.interfaces;

import com.kakao.mobility.kmcoupon.annotation.TimeRecord;
import com.kakao.mobility.kmcoupon.application.CouponService;
import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.domain.member.SecurityUser;
import com.kakao.mobility.kmcoupon.dto.CouponResponse;
import com.kakao.mobility.kmcoupon.dto.CouponUsedResponse;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import com.kakao.mobility.kmcoupon.dto.TimeRecordRequest;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@RestController
@RequestMapping("/api/v1/coupon")
@Api(tags = {"쿠폰 API"})
public class CouponController {
    private static final String DATE_FORMAT = "yyyy-MM-dd HH:mm:ss";

    private final CouponService couponService;

    public CouponController(CouponService couponService) {
        this.couponService = couponService;
    }

    @GetMapping("/usable")
    @ApiOperation(value = "사용가능 쿠폰 조회")
    public ResponseEntity<List<CouponResponse>> listUsableCoupons(
            @TimeRecord TimeRecordRequest timeRecordRequest
    ) {
        SecurityUser principal = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = principal.getMemberId();
        List<Coupon> couponList = couponService.getUsableCouponList(memberId, timeRecordRequest.getRequestReceivedAt());
        List<CouponResponse> couponResponseList = couponList.stream()
                .map(coupon -> {
                    return new CouponResponse(
                            coupon.getId(),
                            coupon.getUseMinAmount(),
                            coupon.getDiscountAmount(),
                            coupon.getUsableFrom().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                            coupon.getUsableUntil().format(DateTimeFormatter.ofPattern(DATE_FORMAT)),
                            coupon.getStatus().name()
                    );
                })
                .collect(Collectors.toList());

        return ResponseEntity.ok(couponResponseList);
    }

    @PostMapping("/use")
    @ApiOperation(value = "쿠폰 사용")
    public ResponseEntity<CouponUsedResponse> useCoupon(@Valid @RequestBody CouponUsingRequest couponUsingRequest) {
        SecurityUser principal = (SecurityUser) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long memberId = principal.getMemberId();
        Coupon usedCoupon = couponService.useCoupon(memberId, couponUsingRequest);

        CouponUsedResponse couponUsedResponse = CouponUsedResponse.makeUsedResponse(usedCoupon, couponUsingRequest);
        return ResponseEntity.ok(couponUsedResponse);
    }
}
