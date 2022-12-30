package com.choimory.itemvaluechecker.api.userapi.member.data.request;

import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberAuthority;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDateTime;

@Builder
@RequiredArgsConstructor
@Getter
public class RequestMemberFindAll {
    private final String identity;
    private final String nickname;
    private final String email;
    private final MemberAuthority.AuthLevel authLevel;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime createdTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime modifiedFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime modifiedTo;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime deletedFrom;
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime deletedTo;
}