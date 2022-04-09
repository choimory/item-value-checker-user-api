package com.choimory.itemvaluechecker.api.userapi.user.entity;

import com.choimory.itemvaluechecker.api.userapi.user.code.AuthLevel;
import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.redis.core.RedisHash;

@RedisHash(value = "user_authority", timeToLive = -1L)
@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class UserAuthority {
    @Id
    private String id;
    private AuthLevel authLevel;
}
