package com.choimory.itemvaluechecker.api.userapi.member.data.response;

import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Builder
@Getter
public class ResponseMemberJoin extends RepresentationModel<ResponseMemberJoin> {
    private final int status;
    private final String message;
    @JsonIgnore
    private final String identity;
    private final String token;

    public ResponseMemberJoin(int status, String message, String identity, String token) {
        this.status = status;
        this.message = message;
        this.identity = identity;
        this.token = token;
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .withSelfRel());
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .slash(identity)
                .withRel("view-identity"));
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .slash("login")
                .withRel("login"));
    }
}
