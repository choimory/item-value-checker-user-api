package com.choimory.itemvaluechecker.api.userapi.member.dto.response;

import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Builder
@Getter
public class MemberJoinResponse extends RepresentationModel<MemberJoinResponse> {
    private final int status;
    private final String message;
    @JsonIgnore
    private final String identity;

    public MemberJoinResponse(int status, String message, String identity) {
        this.status = status;
        this.message = message;
        this.identity = identity;
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .withSelfRel());
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .slash(identity)
                .withRel("view-identity"));
    }
}
