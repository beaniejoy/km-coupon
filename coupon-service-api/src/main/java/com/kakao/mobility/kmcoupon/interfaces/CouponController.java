package com.kakao.mobility.kmcoupon.interfaces;

import com.kakao.mobility.kmcoupon.annotation.TimeRecord;
import com.kakao.mobility.kmcoupon.application.CouponService;
import com.kakao.mobility.kmcoupon.convert.CouponDtoConverter;
import com.kakao.mobility.kmcoupon.domain.coupon.Coupon;
import com.kakao.mobility.kmcoupon.domain.member.SecurityUser;
import com.kakao.mobility.kmcoupon.dto.CouponResponse;
import com.kakao.mobility.kmcoupon.dto.CouponUsedResponse;
import com.kakao.mobility.kmcoupon.dto.CouponUsingRequest;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
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
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<List<CouponResponse>> listUsableCoupons(
            @AuthenticationPrincipal SecurityUser securityUser,
            @TimeRecord LocalDateTime requestReceivedAt
    ) {
        Long memberId = securityUser.getMemberId();
        List<Coupon> couponList = couponService.getUsableCouponList(memberId, requestReceivedAt);
        List<CouponResponse> couponResponseList = couponDtoConvertor.of(couponList);
        return ResponseEntity.ok(couponResponseList);
    }

    @PostMapping("/use")
    @PreAuthorize("hasAnyRole('USER')")
    public ResponseEntity<CouponUsedResponse> useCoupon(
            @Valid @RequestBody CouponUsingRequest couponUsingRequest,
            @AuthenticationPrincipal SecurityUser securityUser,
            @TimeRecord LocalDateTime requestReceivedAt
    ) {
        Long memberId = securityUser.getMemberId();
        couponUsingRequest.recordRequestReceivedTime(requestReceivedAt);
        CouponUsedResponse couponUsedResponse = couponService.useCoupon(memberId, couponUsingRequest);
        return ResponseEntity.ok(couponUsedResponse);
    }
}
