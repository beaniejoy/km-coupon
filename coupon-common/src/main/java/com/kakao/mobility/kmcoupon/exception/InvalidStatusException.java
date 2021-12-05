package com.kakao.mobility.kmcoupon.exception;

public class InvalidStatusException extends BadRequestException {
    public InvalidStatusException(String message) {
        super(message);
    }
}
