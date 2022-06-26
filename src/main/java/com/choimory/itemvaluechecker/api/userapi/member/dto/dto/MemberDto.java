package com.choimory.itemvaluechecker.api.userapi.member.dto.dto;

import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.code.SocialType;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberAuthority;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSocial;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSuspension;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@RequiredArgsConstructor
@Getter
public class MemberDto {
    private final String id;
    private final String name;
    private final String email;
    private final LocalDateTime createdAt;
    private final LocalDateTime modifiedAt;
    private final LocalDateTime deletedAt;
    private final List<MemberSocialDto> memberSocials;
    private final MemberAuthorityDto memberAuthority;
    private final List<MemberSuspensionDto> memberSuspensions;

    public static MemberDto toDto(Member member){
        return MemberDto.builder()
                .id(member.getId())
                .name(member.getName())
                .email(member.getEmail())
                .createdAt(member.getCreatedAt())
                .modifiedAt(member.getModifiedAt())
                .deletedAt(member.getDeletedAt())
                .memberSocials(member.getMemberSocials().stream()
                        .map(MemberSocialDto::toDto)
                        .collect(Collectors.toUnmodifiableList()))
                .memberAuthority(MemberAuthorityDto.toDto(member.getMemberAuthority()))
                .memberSuspensions(member.getMemberSuspensions().stream()
                        .map(MemberSuspensionDto::toDto)
                        .collect(Collectors.toUnmodifiableList()))
                .build();
    }

    @Builder
    @RequiredArgsConstructor
    @Getter
    public static class MemberSocialDto{
        private final SocialType socialType;
        private final String socialId;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;
        private final LocalDateTime deletedAt;

        public static MemberSocialDto toDto(MemberSocial memberSocial){
            return MemberSocialDto.builder()
                    .socialType(memberSocial.getSocialType())
                    .socialId(memberSocial.getSocialId())
                    .createdAt(memberSocial.getCreatedAt())
                    .modifiedAt(memberSocial.getModifiedAt())
                    .deletedAt(memberSocial.getDeletedAt())
                    .build();
        }
    }

    @Builder
    @RequiredArgsConstructor
    @Getter
    public static class MemberAuthorityDto{
        private final AuthLevel authLevel;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;
        private final LocalDateTime deletedAt;

        public static MemberAuthorityDto toDto(MemberAuthority memberAuthority){
            return MemberAuthorityDto.builder()
                    .authLevel(memberAuthority.getAuthLevel())
                    .createdAt(memberAuthority.getCreatedAt())
                    .modifiedAt(memberAuthority.getModifiedAt())
                    .deletedAt(memberAuthority.getDeletedAt())
                    .build();
        }
    }

    @Builder
    @RequiredArgsConstructor
    @Getter
    public static class MemberSuspensionDto{
        private final String reason;
        private final LocalDateTime suspendedAt;
        private final LocalDateTime suspendedTo;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;
        private final LocalDateTime deletedAt;

        public static MemberSuspensionDto toDto(MemberSuspension memberSuspension){
            return MemberSuspensionDto.builder()
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
