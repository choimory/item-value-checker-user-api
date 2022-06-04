package com.choimory.itemvaluechecker.api.userapi.user.dto.response;

import com.choimory.itemvaluechecker.api.userapi.user.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.user.controller.UserController;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.time.LocalDateTime;

@Builder
@Getter
public class UserViewResponse extends RepresentationModel {
    private int status;
    private String message;
    private UserViewResponseUser user;

    public UserViewResponse(int status, String message, UserViewResponseUser user) {
        this.status = status;
        this.message = message;
        this.user = user;
        add(WebMvcLinkBuilder.linkTo(UserController.class)
                .slash(user.getId())
                .withSelfRel());
    }

    @Builder
    @RequiredArgsConstructor
    @Getter
    public static class UserViewResponseUser {
        private final String id;
        private final String name;
        private final String email;
        private final LocalDateTime createdTime;
        private final LocalDateTime modifiedTime;
        private final LocalDateTime deletedTime;
        private final AuthLevel authLevel;
        private final int isSuspended;
        private final String reason;

        public static UserViewResponseUser toDto(User user) {
            return UserViewResponseUser.builder()
                    .id(user.getId())
                    .name(user.getName())
                    .email(user.getEmail())
                    .createdTime(user.getCreatedTime())
                    .modifiedTime(user.getModifiedTime())
                    .deletedTime(user.getDeletedTime())
                    .authLevel(user.getUserAuthority().getAuthLevel())
                    .isSuspended(user.getUserSuspension().getIsSuspended())
                    .reason(user.getUserSuspension().getReason())
                    .build();
        }
    }
}