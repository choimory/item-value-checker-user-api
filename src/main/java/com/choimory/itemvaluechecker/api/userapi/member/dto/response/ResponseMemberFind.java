package com.choimory.itemvaluechecker.api.userapi.member.dto.response;

import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Getter
public class ResponseMemberFind extends RepresentationModel<ResponseMemberFind> {
    private final int status;
    private final String message;
    private final MemberDto member;

    @Builder
    public ResponseMemberFind(int status, String message, MemberDto member) {
        this.status = status;
        this.message = message;
        this.member = member;
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .slash(member.getIdentity())
                .withSelfRel());
    }
}