package com.choimory.itemvaluechecker.api.userapi.member.dto.response;

import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.code.SocialType;
import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSocial;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSuspension;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Getter
public class MemberViewResponse extends RepresentationModel {
    private final int status;
    private final String message;
    private final MemberDto member;

    @Builder
    public MemberViewResponse(int status, String message, MemberDto member) {
        this.status = status;
        this.message = message;
        this.member = member;
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .slash(member.getMemberId())
                .withSelfRel());
    }
}