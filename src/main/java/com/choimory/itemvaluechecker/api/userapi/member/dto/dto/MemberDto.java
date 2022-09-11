package com.choimory.itemvaluechecker.api.userapi.member.dto.dto;

import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.code.SocialType;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberAuthority;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSocial;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSuspension;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
@JsonInclude(JsonInclude.Include.NON_NULL)
public class MemberDto {
    private Long id;
    private String identity;
    @JsonIgnore
    private String password;
    private String nickname;
    private String email;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime deletedAt;
    private MemberAuthorityDto memberAuthority;
    private List<MemberSocialDto> memberSocials;
    private List<MemberSuspensionDto> memberSuspensions;

    private List<MemberSocial> socials;

    public static MemberDto toDto(Member member){
        return member == null
                ? null
                : MemberDto.builder()
                .id(member.getId())
                .identity(member.getIdentity())
                .password(member.getPassword())
                .nickname(member.getNickname())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .deletedAt(member.getDeletedAt())
                .memberSocials(member.getMemberSocials()
                        .stream()
                        .map(MemberSocialDto::toDto)
                        .collect(Collectors.toUnmodifiableList()))
                .memberAuthority(MemberAuthorityDto.toDto(member.getMemberAuthority()))
                .memberSuspensions(member.getMemberSuspensions()
                        .stream()
                        .map(MemberSuspensionDto::toDto)
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MemberSocialDto {
        private SocialType socialType;
        private String socialId;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime deletedAt;

        public static MemberSocialDto toDto(MemberSocial memberSocial){
            return memberSocial == null
                    ? null
                    : MemberSocialDto.builder()
                    .socialType(memberSocial.getSocialType())
                    .socialId(memberSocial.getSocialId())
                    .createdAt(memberSocial.getCreatedAt())
                    .modifiedAt(memberSocial.getModifiedAt())
                    .deletedAt(memberSocial.getDeletedAt())
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MemberAuthorityDto{
        private AuthLevel authLevel;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime deletedAt;

        public static MemberAuthorityDto toDto(MemberAuthority memberAuthority){
            return memberAuthority == null
                    ? null
                    : MemberAuthorityDto.builder()
                    .authLevel(memberAuthority.getAuthLevel())
                    .createdAt(memberAuthority.getCreatedAt())
                    .modifiedAt(memberAuthority.getModifiedAt())
                    .deletedAt(memberAuthority.getDeletedAt())
                    .build();
        }
    }

    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    @Getter
    @JsonInclude(JsonInclude.Include.NON_NULL)
    public static class MemberSuspensionDto{
        private String reason;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime suspendedAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime suspendedTo;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime createdAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime modifiedAt;
        @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
        private LocalDateTime deletedAt;

        public static MemberSuspensionDto toDto(MemberSuspension memberSuspension){
            return memberSuspension == null
                    ? null
                    : MemberSuspensionDto.builder()
                    .reason(memberSuspension.getReason())
                    .suspendedAt(memberSuspension.getSuspendedAt())
                    .suspendedTo(memberSuspension.getSuspendedTo())
                    .createdAt(memberSuspension.getCreatedAt())
                    .modifiedAt(memberSuspension.getModifiedAt())
                    .deletedAt(memberSuspension.getDeletedAt())
                    .build();
        }
    }
}
