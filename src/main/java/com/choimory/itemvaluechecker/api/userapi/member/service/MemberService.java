package com.choimory.itemvaluechecker.api.userapi.member.service;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberJoinRequest;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberListRequest;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberJoinResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberListResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberViewResponse;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.repository.MemberRepository;
import com.choimory.itemvaluechecker.api.userapi.member.valid.MemberValid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
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

    public MemberViewResponse view(final String memberId){
        return MemberViewResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .member(MemberDto.toDto(memberRepository.findMemberByIdentityEquals(memberId)
                        .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()))))
                .build();
    }

    public MemberListResponse views(final MemberListRequest param, final Pageable pageable){
        Page<Member> members = memberRepository.findAll(pageable, param.getIdentity(), param.getNickname(), param.getEmail(), param.getAuthLevel(), param.getCreatedFrom(), param.getCreatedTo(), param.getModifiedFrom(), param.getModifiedTo(), param.getDeletedFrom(), param.getDeletedTo());

        if(members.isEmpty()){
            throw new CommonException(HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        }

        return MemberListResponse.builder()
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
    public MemberJoinResponse join(final MemberJoinRequest param) throws Exception {
        /*???????????? ??????*/
        if(memberRepository.existsByIdentity(param.getIdentity())){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberValid.CODE_ID_DUPLICATE,
                    MemberValid.MESSAGE_ID_DUPLICATE);
        }

        /*??????*/
        Member member = memberRepository.save(param.toEntity(passwordEncoder));

        /*??????*/
        return MemberJoinResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .identity(member.getIdentity())
                .build();
    }
}
