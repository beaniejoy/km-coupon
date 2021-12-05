package com.kakao.mobility.kmcoupon.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberRegistrationResponse {

    private String email;

    private String role;
}
