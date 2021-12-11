package com.kakao.mobility.kmcoupon

import org.springframework.boot.autoconfigure.SpringBootApplication
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import com.kakao.mobility.kmcoupon.CouponLoginApiApplication
import java.util.*
import javax.annotation.PostConstruct

@SpringBootApplication
class CouponLoginApiApplication {
    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(CouponLoginApiApplication::class.java, *args)
        }
    }

    @PostConstruct
    fun started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }
}