package com.kakao.mobility.kmcoupon.repository;

import com.kakao.mobility.kmcoupon.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findByEmail(String email);
}
