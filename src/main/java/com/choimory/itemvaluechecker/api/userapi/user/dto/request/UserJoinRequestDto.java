package com.choimory.itemvaluechecker.api.userapi.user.dto.request;

import com.choimory.itemvaluechecker.api.userapi.user.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserAuthority;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserSuspension;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Getter
public class UserJoinRequestDto {
    private final String id;
    private final String password;
    private final String name;
    private final String email;
    private final AuthLevel authLevel;

    public User toEntity(){
        return User.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .createdTime(LocalDateTime.now())
                .modifiedTime(null)
                .deletedTime(null)
                .userAuthority(UserAuthority.builder()
                        .id(id)
                        .authLevel(authLevel)
                        .build())
                .userSuspension(UserSuspension.builder()
                        .id(id)
                        .isSuspended(0)
                        .suspendedTime(null)
                        .reason(null)
                        .build())
                .build();
    }

    public boolean isIdValidate(){
        return true;
    }

    public boolean isPasswordValidate(){
        return true;
    }

    public boolean isEmailValidate(){
        return true;
    }
}
