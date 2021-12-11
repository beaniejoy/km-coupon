package com.kakao.mobility.kmcoupon.interfaces

import com.kakao.mobility.kmcoupon.application.MemberService
import com.kakao.mobility.kmcoupon.dto.request.MemberRegistrationRequest
import com.kakao.mobility.kmcoupon.dto.response.MemberRegistrationResponse
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@RequestMapping("/api/v1/member")
@Api(tags = ["회원관리 API"])
class MemberController(
    private val memberService: MemberService
) {
    @PostMapping("/signup")
    @ApiOperation(value = "회원가입")
    fun signUp(
        @Valid @RequestBody registrationRequest: MemberRegistrationRequest
    ): ResponseEntity<MemberRegistrationResponse> {
        val created = memberService.registerMember(registrationRequest)
        val registrationResponse = MemberRegistrationResponse(created.email, created.role.name)
        return ResponseEntity.ok(registrationResponse)
    }
}