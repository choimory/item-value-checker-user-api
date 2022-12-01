package com.choimory.itemvaluechecker.api.userapi.jwt;

import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto;
import com.choimory.itemvaluechecker.api.userapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * JWT에서 사용할 회원 관련 로직
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class JwtMemberService {
    private final MemberRepository memberRepository;

    public MemberDto findMemberByIdentity(String identity){
        return MemberDto.toDto(memberRepository.findMemberByIdentityEquals(identity)
                .orElse(null));
    }
}
