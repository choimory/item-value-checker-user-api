package com.choimory.itemvaluechecker.api.userapi.member.dto.response;

import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Builder
@Getter
public class MemberJoinResponse extends RepresentationModel<MemberJoinResponse> {
    private final int status;
    private final String message;

    public MemberJoinResponse(int status, String message) {
        this.status = status;
        this.message = message;
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .withSelfRel());
    }
}
