package com.kakao.mobility.kmcoupon.exception;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class CommonControllerAdvice {
    @ExceptionHandler(value = BadRequestException.class)
    public ResponseEntity<String> handleNotFoundException(BadRequestException e) {
        return ResponseEntity.badRequest().body(e.getMessage());
    }
}
