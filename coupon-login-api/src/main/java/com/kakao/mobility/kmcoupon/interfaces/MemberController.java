package com.kakao.mobility.kmcoupon.interfaces;

import com.kakao.mobility.kmcoupon.application.MemberService;
import com.kakao.mobility.kmcoupon.domain.member.Member;
import com.kakao.mobility.kmcoupon.dto.request.MemberRegistrationRequest;
import com.kakao.mobility.kmcoupon.dto.response.MemberRegistrationResponse;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequestMapping("/api/v1/member")
@Api(tags = {"회원관리 API"})
public class MemberController {

    private final MemberService memberService;

    public MemberController(MemberService memberService) {
        this.memberService = memberService;
    }

    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    public ResponseEntity<MemberRegistrationResponse> signUp(
            @Valid @RequestBody MemberRegistrationRequest registrationRequest) {
        Member created = memberService.registerMember(registrationRequest);
        MemberRegistrationResponse registrationResponse = new MemberRegistrationResponse(created.getEmail(), created.getRole().name());

        return ResponseEntity.ok(registrationResponse);
    }
}
