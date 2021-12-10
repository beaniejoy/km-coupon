package com.kakao.mobility.kmcoupon

import org.springframework.boot.autoconfigure.SpringBootApplication
import java.util.TimeZone
import kotlin.jvm.JvmStatic
import org.springframework.boot.SpringApplication
import com.kakao.mobility.kmcoupon.CouponServiceApiApplication
import javax.annotation.PostConstruct

@SpringBootApplication
class CouponServiceApiApplication {
    @PostConstruct
    fun started() {
        TimeZone.setDefault(TimeZone.getTimeZone("Asia/Seoul"))
    }

    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication.run(CouponServiceApiApplication::class.java, *args)
        }
    }
}