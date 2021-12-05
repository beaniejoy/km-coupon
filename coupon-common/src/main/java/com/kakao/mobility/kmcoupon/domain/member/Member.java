package com.kakao.mobility.kmcoupon.domain.member;

import com.kakao.mobility.kmcoupon.domain.BaseTimeEntity;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Getter
@NoArgsConstructor
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

    @Builder
    public Member(String email, String password) {
        // TODO 제약 조건 추가
        this.email = email;
        this.password = password;

        this.role = Role.USER;
    }
}
