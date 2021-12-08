package com.kakao.mobility.kmcoupon.dto.response;

public class MemberRegistrationResponse {

    private String email;

    private String role;

    public MemberRegistrationResponse(String email, String role) {
        this.email = email;
        this.role = role;
    }

    public String getEmail() {
        return email;
    }

    public String getRole() {
        return role;
    }
}
