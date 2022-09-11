package com.choimory.itemvaluechecker.api.userapi.member.dto.response;

import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberSuspensionDto;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSuspension;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

@Builder
@Getter
public class ResponseMemberLogin extends RepresentationModel<ResponseMemberLogin> {
    private String identity;
    private List<MemberSuspensionDto> suspensions;
    private String token;

    public ResponseMemberLogin(String identity, List<MemberSuspensionDto> suspensions, String token) {
        this.identity = identity;
        this.suspensions = suspensions;
        this.token = token;
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .withSelfRel());
        add(WebMvcLinkBuilder.linkTo(MemberController.class)
                .slash(identity)
                .withRel("my-page"));
    }

    public static boolean isSuspended(List<MemberSuspensionDto> suspensions){
        return !suspensions.stream()
                .filter(suspension -> suspension.getSuspendedTo().isAfter(LocalDateTime.now()))
                .collect(Collectors.toUnmodifiableList())
                .isEmpty();
    }
}
