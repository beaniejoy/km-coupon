package com.kakao.mobility.kmcoupon.dto;

import com.kakao.mobility.kmcoupon.domain.coupon.Status;
import com.kakao.mobility.kmcoupon.domain.member.Role;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class CouponWithMemberDto {
    private Long id;
    private BigDecimal useMinAmount;
    private BigDecimal discountAmount;
    private LocalDateTime usableFrom;
    private LocalDateTime usableUntil;
    private Status status;
    private Long memberId;
    private String email;
    private Role role;

    public CouponWithMemberDto() {
    }

    public CouponWithMemberDto(Long id, BigDecimal useMinAmount, BigDecimal discountAmount, LocalDateTime usableFrom, LocalDateTime usableUntil, Status status, Long memberId, String email, Role role) {
        this.id = id;
        this.useMinAmount = useMinAmount;
        this.discountAmount = discountAmount;
        this.usableFrom = usableFrom;
        this.usableUntil = usableUntil;
        this.status = status;
        this.memberId = memberId;
        this.email = email;
        this.role = role;
    }

    public Long getId() {
        return id;
    }

    public BigDecimal getUseMinAmount() {
        return useMinAmount;
    }

    public BigDecimal getDiscountAmount() {
        return discountAmount;
    }

    public LocalDateTime getUsableFrom() {
        return usableFrom;
    }

    public LocalDateTime getUsableUntil() {
        return usableUntil;
    }

    public Status getStatus() {
        return status;
    }

    public Long getMemberId() {
        return memberId;
    }

    public String getEmail() {
        return email;
    }

    public Role getRole() {
        return role;
    }
}
