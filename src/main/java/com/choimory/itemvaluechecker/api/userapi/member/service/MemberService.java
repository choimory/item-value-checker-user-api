package com.choimory.itemvaluechecker.api.userapi.member.service;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberJoinRequest;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberListRequest;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberJoinResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberListResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberViewResponse;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;

    public MemberViewResponse view(final String memberId){
        return MemberViewResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .member(MemberViewResponse.MemberViewResponseMember.toDto(memberRepository.findByMemberId(memberId)
                        .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()))))
                .build();
    }

    public MemberListResponse views(final MemberListRequest param, final Pageable pageable){
        return null;
    }

    @Transactional
    public MemberJoinResponse join(final MemberJoinRequest param) throws Exception {
        /*필수값 검증*/
        param.requiredArgsValidate();

        /*요청값 검증*/
        param.isIdValidate();
        //param.isPasswordValidate();
        param.isEmailValidate();

        /*중복여부 확인*/
        if(memberRepository.existsByMemberId(param.getMemberId())){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequest.MemberJoinRequestValidate.ID_DUPLICATE.getCode(),
                    MemberJoinRequest.MemberJoinRequestValidate.ID_DUPLICATE.getMessage());
        }

        /*저장*/
        Member member = memberRepository.save(param.toEntity());

        /*반환*/
        MemberJoinResponse response = MemberJoinResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
        response.add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .slash(member.getMemberId())
                .withRel("view-id"));

        return response;
    }
}
