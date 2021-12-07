package com.kakao.mobility.kmcoupon.dto;

import java.time.LocalDateTime;

public class TimeRecordRequest {
    private final LocalDateTime requestReceivedAt;

    public TimeRecordRequest(LocalDateTime requestReceivedAt) {
        this.requestReceivedAt = requestReceivedAt;
    }

    public LocalDateTime getRequestReceivedAt() {
        return requestReceivedAt;
    }
}
