package com.kakao.mobility.kmcoupon.convert;

import com.kakao.mobility.kmcoupon.domain.member.Member;
import com.kakao.mobility.kmcoupon.dto.response.MemberRegistrationResponse;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(
        componentModel = "spring",
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MemberDtoConverter {
    @Mapping(expression = "java(member.getRole().name())", target = "role")
    MemberRegistrationResponse of(Member member);
}
