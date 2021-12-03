package com.kakao.mobility.kmcoupon.domain;

public enum Status {
    NORMAL("사용가능"), USED("사용됨");

    private final String value;

    Status(String value) {
        this.value = value;
    }

    public String getValue() {
        return value;
    }
}
