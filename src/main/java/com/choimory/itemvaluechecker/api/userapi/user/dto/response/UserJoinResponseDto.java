package com.choimory.itemvaluechecker.api.userapi.user.dto.response;

import com.choimory.itemvaluechecker.api.userapi.user.controller.UserController;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

@Builder
@Getter
public class UserJoinResponseDto extends RepresentationModel {
    private final int status;
    private final String message;
    private final String token;

    public UserJoinResponseDto(int status, String message, String token) {
        this.status = status;
        this.message = message;
        this.token = token;
        add(WebMvcLinkBuilder.linkTo(UserController.class)
                .withSelfRel());
    }
}
