package com.kakao.mobility.kmcoupon.common.exception;

public class EntityNotFoundException extends BadRequestException {
    public EntityNotFoundException(String message) {
        super(message);
    }
}
