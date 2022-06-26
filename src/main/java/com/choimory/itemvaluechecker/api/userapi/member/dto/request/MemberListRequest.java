package com.choimory.itemvaluechecker.api.userapi.member.dto.request;

import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@Getter
public class MemberListRequest {
    private final String id;
    private final String name;
    private final String email;
    private final AuthLevel authLevel;
    private final LocalDateTime createdFrom;
    private final LocalDateTime createdTo;
    private final LocalDateTime modifiedFrom;
    private final LocalDateTime modifiedTo;
    private final LocalDateTime deletedFrom;
    private final LocalDateTime deletedTo;
}