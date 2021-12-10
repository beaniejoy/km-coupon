package com.kakao.mobility.kmcoupon.domain.member

import com.kakao.mobility.kmcoupon.domain.BaseTimeEntity
import javax.persistence.*

@Entity
@Table(name = "member")
class Member(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0L,

    @Column(name = "email")
    val email: String,

    @Column(name = "password")
    val password: String,

    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    var role: Role = Role.ROLE_USER
) : BaseTimeEntity()