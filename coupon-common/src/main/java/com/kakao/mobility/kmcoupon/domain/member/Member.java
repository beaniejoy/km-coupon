package com.kakao.mobility.kmcoupon.domain.member;

import com.kakao.mobility.kmcoupon.domain.BaseTimeEntity;

import javax.persistence.*;

@Entity
@Table(name = "member")
public class Member extends BaseTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public Member() {
    }

    public Member(String email, String password) {
        // TODO 제약 조건 추가
        this.email = email;
        this.password = password;

        this.role = Role.ROLE_USER;
    }

    public Long getId() {
        return id;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }

    public Role getRole() {
        return role;
    }
}
