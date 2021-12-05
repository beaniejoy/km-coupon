package com.kakao.mobility.kmcoupon.application;

import com.kakao.mobility.kmcoupon.domain.member.Member;
import com.kakao.mobility.kmcoupon.dto.request.MemberRegistrationRequest;
import com.kakao.mobility.kmcoupon.exception.InvalidRequestException;
import com.kakao.mobility.kmcoupon.repository.MemberRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    @Transactional
    public Member registerMember(MemberRegistrationRequest registrationRequest) {
        String email = registrationRequest.getEmail();
        memberRepository.findByEmail(email)
                .ifPresent(member -> {
                    throw new InvalidRequestException(String.format("해당 이메일[%s]은 이미 등록된 계정입니다.", email));
                });

        return memberRepository.save(createMember(registrationRequest));
    }

    private Member createMember(MemberRegistrationRequest registrationRequest) {
        return new Member(
                registrationRequest.getEmail(),
                passwordEncoder.encode(registrationRequest.getPassword()));
    }
}
