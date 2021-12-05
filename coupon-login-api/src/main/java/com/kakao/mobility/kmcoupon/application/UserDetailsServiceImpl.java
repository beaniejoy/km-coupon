package com.kakao.mobility.kmcoupon.application;

import com.kakao.mobility.kmcoupon.domain.member.Member;
import com.kakao.mobility.kmcoupon.domain.member.SecurityUser;
import com.kakao.mobility.kmcoupon.exception.InvalidRequestException;
import com.kakao.mobility.kmcoupon.repository.MemberRepository;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;

@Component("userDetailsService")
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;

    public UserDetailsServiceImpl(MemberRepository memberRepository) {
        this.memberRepository = memberRepository;
    }

    @Override
    @Transactional
    public SecurityUser loadUserByUsername(String email) throws UsernameNotFoundException {
        return memberRepository.findByEmail(email)
                .map(this::createSecurityUser)
                .orElseThrow(() -> new InvalidRequestException("이메일 계정이 존재하지 않습니다."));
    }

    private SecurityUser createSecurityUser(Member member) {
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(member.getRole().name());
        List<GrantedAuthority> grantedAuthorityList = Collections.singletonList(grantedAuthority);
        return new SecurityUser(member.getId(), member.getEmail(), member.getPassword(), grantedAuthorityList);
    }
}
