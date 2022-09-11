package com.choimory.itemvaluechecker.api.userapi.member.service;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.jwt.TokenManager;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.RequestMemberJoin;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.RequestMemberList;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.ResponseMemberJoin;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.ResponseMemberList;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.ResponseMemberView;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.repository.MemberRepository;
import com.choimory.itemvaluechecker.api.userapi.member.valid.MemberValid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    public ResponseMemberView view(final String memberId){
        return ResponseMemberView.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .member(MemberDto.toDto(memberRepository.findMemberByIdentityEquals(memberId)
                        .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()))))
                .build();
    }

    public ResponseMemberList views(final RequestMemberList param, final Pageable pageable){
        Page<Member> members = memberRepository.findAll(pageable, param.getIdentity(), param.getNickname(), param.getEmail(), param.getAuthLevel(), param.getCreatedFrom(), param.getCreatedTo(), param.getModifiedFrom(), param.getModifiedTo(), param.getDeletedFrom(), param.getDeletedTo());

        if(members.isEmpty()){
            throw new CommonException(HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        }

        return ResponseMemberList.builder()
                .page(members.getNumber()+1)
                .size(members.getSize())
                .sort(members.getSort().toString())
                .totalPage(members.getTotalPages())
                .totalCount(members.getTotalElements())
                .members(members.getContent()
                        .stream()
                        .map(MemberDto::toDto)
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    @Transactional
    public ResponseMemberJoin join(final RequestMemberJoin param) {
        /*중복여부 확인*/
        if(memberRepository.existsByIdentity(param.getIdentity())){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberValid.CODE_ID_DUPLICATE,
                    MemberValid.MESSAGE_ID_DUPLICATE);
        }

        /*저장*/
        Member member = memberRepository.save(param.toEntity(passwordEncoder));

        /*반환*/
        return ResponseMemberJoin.builder()
                .status(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .identity(member.getIdentity())
                .token(tokenManager.generateToken(member.getIdentity()))
                .build();
    }
}
