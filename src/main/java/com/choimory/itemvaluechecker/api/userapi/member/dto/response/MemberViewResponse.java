package com.choimory.itemvaluechecker.api.userapi.member.dto.response;

import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.code.SocialType;
import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSocial;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSuspension;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class MemberViewResponse extends RepresentationModel {
    private int status;
    private String message;
    private MemberViewResponseMember member;

    public MemberViewResponse(int status, String message, MemberViewResponseMember member) {
        this.status = status;
        this.message = message;
        this.member = member;
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .slash(member.getId())
                .withSelfRel());
    }

    @Builder
    @RequiredArgsConstructor
    @Getter
    public static class MemberViewResponseMember {
        private final String id;
        private final String name;
        private final String email;
        private final LocalDateTime createdAt;
        private final LocalDateTime modifiedAt;
        private final LocalDateTime deletedAt;
        private final AuthLevel authLevel;
        private final List<MemberViewResponseSocial> socials;
        private final List<MemberViewResponseSuspension> suspensions;

        @Builder
        @RequiredArgsConstructor
        @Getter
        public static class MemberViewResponseSocial {
            private final SocialType socialType;
            private final String socialId;

            public static MemberViewResponseSocial toDto(MemberSocial memberSocial){
                return MemberViewResponseSocial.builder()
                        .socialType(memberSocial.getSocialType())
                        .socialId(memberSocial.getSocialId())
                        .build();
            }
        }

        @Builder
        @RequiredArgsConstructor
        @Getter
        public static class MemberViewResponseSuspension {
            private final String reason;
            private final LocalDateTime suspendedAt;
            private final LocalDateTime suspendedTo;

            public static MemberViewResponseSuspension toDto(MemberSuspension memberSuspension){
                return MemberViewResponseSuspension.builder()
                        .reason(memberSuspension.getReason())
                        .suspendedTo(memberSuspension.getSuspendedTo())
                        .suspendedAt(memberSuspension.getSuspendedAt())
                        .build();
            }
        }

        public static MemberViewResponseMember toDto(Member member) {
            return MemberViewResponseMember.builder()
                    .id(member.getId())
                    .name(member.getName())
                    .email(member.getEmail())
                    .createdAt(member.getCreatedAt())
                    .modifiedAt(member.getModifiedAt())
                    .deletedAt(member.getDeletedAt())
                    .authLevel(member.getMemberAuthority().getAuthLevel())
                    .socials(member.getMemberSocials().stream()
                            .map(MemberViewResponseSocial::toDto)
                            .collect(Collectors.toUnmodifiableList()))
                    .suspensions(member.getMemberSuspensions().stream()
                            .map(MemberViewResponseSuspension::toDto)
                            .collect(Collectors.toUnmodifiableList()))
                    .build();
        }
    }
}