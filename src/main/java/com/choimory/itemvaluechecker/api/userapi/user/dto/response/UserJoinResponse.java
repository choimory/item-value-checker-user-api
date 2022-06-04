package com.choimory.itemvaluechecker.api.userapi.user.dto.response;

import com.choimory.itemvaluechecker.api.userapi.user.controller.UserController;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Builder
@Getter
public class UserJoinResponse extends RepresentationModel {
    private final int status;
    private final String message;

    public UserJoinResponse(int status, String message) {
        this.status = status;
        this.message = message;
        add(WebMvcLinkBuilder.linkTo(UserController.class)
                .withSelfRel());
    }
}
