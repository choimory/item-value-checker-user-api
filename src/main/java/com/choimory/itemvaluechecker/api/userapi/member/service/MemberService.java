package com.choimory.itemvaluechecker.api.userapi.member.service;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.jwt.TokenManager;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto.MemberSuspensionDto;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.RequestMemberBan;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.RequestMemberFindAll;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.RequestMemberJoin;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.RequestMemberLogin;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.*;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSuspension;
import com.choimory.itemvaluechecker.api.userapi.member.repository.MemberRepository;
import com.choimory.itemvaluechecker.api.userapi.member.valid.MemberValid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenManager tokenManager;

    public ResponseMemberFind find(final String memberId){
        return ResponseMemberFind.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .member(MemberDto.toDto(memberRepository.findMemberByIdentityEquals(memberId)
                        .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()))))
                .build();
    }

    public ResponseMemberFindAll findAll(final RequestMemberFindAll param, final Pageable pageable){
        Page<Member> members = memberRepository.findAll(pageable, param.getIdentity(), param.getNickname(), param.getEmail(), param.getAuthLevel(), param.getCreatedFrom(), param.getCreatedTo(), param.getModifiedFrom(), param.getModifiedTo(), param.getDeletedFrom(), param.getDeletedTo());

        if(members.isEmpty()){
            throw new CommonException(HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase());
        }

        return ResponseMemberFindAll.builder()
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

    public ResponseMemberLogin login(final RequestMemberLogin param){
        MemberDto member = MemberDto.toDto(memberRepository.findMemberByIdentityEqualsAndDeletedAtIsNull(param.getIdentity())
                .orElseThrow(() -> new CommonException(HttpStatus.NO_CONTENT,
                        HttpStatus.NO_CONTENT.value(),
                        HttpStatus.NO_CONTENT.getReasonPhrase())));

        /*비밀번호 체크*/
        boolean isPasswordMatched = param.isPasswordMatched(passwordEncoder, member.getPassword());
        if(!isPasswordMatched){
            throw new CommonException(HttpStatus.NO_CONTENT,
                    HttpStatus.NO_CONTENT.value(),
                    HttpStatus.NO_CONTENT.getReasonPhrase());
        }

        /*정지여부 확인*/
        List<MemberSuspensionDto> activateSuspensions = ResponseMemberLogin.findActivateSuspensions(member.getMemberSuspensions());

        /*반환*/
        return CollectionUtils.isEmpty(activateSuspensions)
                ? ResponseMemberLogin.builder()
                .identity(member.getIdentity())
                .token(tokenManager.generateToken(member.getIdentity()))
                .build()
                : ResponseMemberLogin.builder()
                .identity(member.getIdentity())
                .suspensions(activateSuspensions)
                .build();
    }

    @Transactional // 리드온리 트랜잭션은 엔티티 더티체크 되어도 수정 쿼리 안날아감
    public ResponseMemberBan ban(final String identity, final RequestMemberBan param){
        Member member = memberRepository.findMemberByIdentityEquals(identity)
                .orElseThrow(() -> new CommonException(HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.value(), HttpStatus.NO_CONTENT.getReasonPhrase()));

        /*이미 정지된 회원여부인지 확인*/
        boolean isSuspendActivated = RequestMemberBan.isSuspendActivated(member.getMemberSuspensions());
        if(RequestMemberBan.isSuspendActivated(member.getMemberSuspensions())){
            throw new CommonException(HttpStatus.BAD_REQUEST, HttpStatus.BAD_REQUEST.value(), HttpStatus.BAD_REQUEST.getReasonPhrase());
        }

        /*정지 처리 진행*/
        LocalDateTime suspendedAt = LocalDateTime.now();
        //cascade
        member.getMemberSuspensions()
                .add(MemberSuspension.builder()
                        .member(member)
                        .suspendedAt(suspendedAt)
                        .suspendedTo(param.getSuspendTo())
                        .reason(param.getReason())
                        .build());

        /*반환*/
        return ResponseMemberBan.builder()
                .identity(identity)
                .suspendedAt(suspendedAt)
                .suspendedTo(param.getSuspendTo())
                .reason(param.getReason())
                .build();
    }
}
