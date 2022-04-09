package com.choimory.itemvaluechecker.api.userapi.user.entity;

import lombok.*;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(value = "user", timeToLive = -1L)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createdTime;
    private LocalDateTime modifiedTime;
    private LocalDateTime deletedTime;
    private UserAuthority userAuthority;
    private UserSuspension userSuspension;
}
