package com.kakao.mobility.kmcoupon.domain.member;

public enum Role {
    ROLE_ADMIN("어드민"), ROLE_USER("사용자");

    private final String value;

    Role(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
