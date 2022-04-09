package com.choimory.itemvaluechecker.api.userapi.user.entity;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

import java.time.LocalDateTime;

@RedisHash(value = "user_suspension", timeToLive = -1L)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserSuspension {
    @Id
    private String id;
    private int isSuspended;
    private LocalDateTime suspendedTime;
    private String reason;
}
