package com.kakao.mobility.kmcoupon.controller

import com.kakao.mobility.kmcoupon.service.AuthService
import com.kakao.mobility.kmcoupon.dto.request.LoginRequest
import com.kakao.mobility.kmcoupon.dto.response.TokenResponse
import com.kakao.mobility.kmcoupon.util.JwtTokenUtil
import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.springframework.http.ResponseEntity
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController
import javax.validation.Valid

@RestController
@Api(tags = ["인증 API"])
class AuthController(
    private val authService: AuthService,
    private val jwtTokenUtil: JwtTokenUtil
) {
    @PostMapping("/api/v1/auth")
    @ApiOperation(value = "로그인")
    fun signIn(@Valid @RequestBody loginRequest: LoginRequest): ResponseEntity<TokenResponse> {
        val authenticatedMember = authService.authenticateMember(
            email = loginRequest.email,
            password = loginRequest.password
        )

        SecurityContextHolder.getContext().authentication = authenticatedMember
        val token = jwtTokenUtil.createToken(authenticatedMember)
        return ResponseEntity.ok(TokenResponse(token))
    }
}