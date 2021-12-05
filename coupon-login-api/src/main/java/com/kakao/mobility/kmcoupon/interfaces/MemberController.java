package com.kakao.mobility.kmcoupon.interfaces;

import com.kakao.mobility.kmcoupon.application.MemberService;
import com.kakao.mobility.kmcoupon.convert.MemberDtoConverter;
import com.kakao.mobility.kmcoupon.domain.member.Member;
import com.kakao.mobility.kmcoupon.dto.request.MemberRegistrationRequest;
import com.kakao.mobility.kmcoupon.dto.response.MemberRegistrationResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/member")
public class MemberController {

    private final MemberService memberService;
    private final MemberDtoConverter memberDtoConverter;

    public MemberController(MemberService memberService, MemberDtoConverter memberDtoConverter) {
        this.memberService = memberService;
        this.memberDtoConverter = memberDtoConverter;
    }

    @PostMapping("/signup")
    public ResponseEntity<MemberRegistrationResponse> signUp(
            @Valid @RequestBody MemberRegistrationRequest registrationRequest) {
        Member created = memberService.registerMember(registrationRequest);
        MemberRegistrationResponse registrationResponse = memberDtoConverter.of(created);
        return ResponseEntity.ok(registrationResponse);
    }
}
