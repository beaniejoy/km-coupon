package com.kakao.mobility.kmcoupon.domain.member;

public enum Role {
    ADMIN("어드민"), USER("사용자");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
