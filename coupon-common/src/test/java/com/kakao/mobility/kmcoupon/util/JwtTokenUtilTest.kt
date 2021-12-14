package com.kakao.mobility.kmcoupon.util

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.Test
import java.util.stream.Collectors

internal class JwtTokenUtilTest {

    @Test
    fun jointToStringTest() {
        val authorities = listOf("ROLE_USER", "ROLE_ADMIN")
        println(authorities.joinToString(separator = ","))

        println(authorities.stream().collect(Collectors.joining(",")))
    }
}