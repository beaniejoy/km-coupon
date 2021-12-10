package com.kakao.mobility.kmcoupon.exception

open class BadRequestException : RuntimeException {
    constructor() : super("잘못된 요청입니다.") {}
    constructor(message: String) : super(message) {}
    constructor(message: String, cause: Throwable) : super(message, cause) {}
}