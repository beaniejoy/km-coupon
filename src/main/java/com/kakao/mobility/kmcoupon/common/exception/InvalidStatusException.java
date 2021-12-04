package com.kakao.mobility.kmcoupon.common.exception;

public class InvalidStatusException extends BadRequestException {
    public InvalidStatusException(String message) {
        super(message);
    }
}
