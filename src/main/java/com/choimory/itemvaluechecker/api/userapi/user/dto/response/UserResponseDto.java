package com.choimory.itemvaluechecker.api.userapi.user.dto.response;

import com.choimory.itemvaluechecker.api.userapi.user.controller.UserController;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;

public class UserResponseDto {
    @Builder
    @Getter
    public static class View extends RepresentationModel {
        private int status;
        private String message;
        private User user;

        public View(int status, String message, User user) {
            this.status = status;
            this.message = message;
            this.user = user;
            add(WebMvcLinkBuilder.linkTo(UserController.class)
                    .slash(user.getId())
                    .withSelfRel());
        }
    }

    @Builder
    @Getter
    public static class Join extends RepresentationModel {
        private final int status;
        private final String message;
        private final String token;

        public Join(int status, String message, String token) {
            this.status = status;
            this.message = message;
            this.token = token;
            add(WebMvcLinkBuilder.linkTo(UserController.class)
                    .withSelfRel());
        }
    }

    @Builder
    @Getter
    @RequiredArgsConstructor
    public static class Login extends RepresentationModel {
        private final int status;
        private final String message;
        private final String token;
    }
}
