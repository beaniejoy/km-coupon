package com.kakao.mobility.kmcoupon.exception;

public class EntityNotFoundException extends BadRequestException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
