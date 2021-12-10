package com.kakao.mobility.kmcoupon.common.filter

import com.kakao.mobility.kmcoupon.util.JwtTokenUtil
import mu.KLogging
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.GenericFilterBean
import java.io.IOException
import javax.servlet.FilterChain
import javax.servlet.ServletException
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class JwtAuthenticationFilter(
    private val jwtTokenUtil: JwtTokenUtil
) : GenericFilterBean() {

    companion object : KLogging() {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX_COUNT = 7
    }

    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val httpServletRequest = request as HttpServletRequest
        val token = extractToken(httpServletRequest)
        if (StringUtils.hasText(token) && jwtTokenUtil.validateJwtToken(token)) {
            val authentication = jwtTokenUtil.getAuthentication(token)
            SecurityContextHolder.getContext().authentication = authentication
            logger.info("SecurityContextHolder 에 인증 내역이 정상적으로 저장되었습니다.")
        }
        chain.doFilter(request, response)
    }

    private fun extractToken(request: HttpServletRequest): String? {
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        return if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            bearerToken.substring(BEARER_PREFIX_COUNT)
        } else null
    }
}