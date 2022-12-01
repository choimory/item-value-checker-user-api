package com.choimory.itemvaluechecker.api.userapi.member.dto.response;

import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto.MemberSuspensionDto;
import lombok.Builder;
import lombok.Getter;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
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
        if(CollectionUtils.isEmpty(suspensions)) {
            add(WebMvcLinkBuilder.linkTo(MemberController.class)
                    .withSelfRel());
            add(WebMvcLinkBuilder.linkTo(MemberController.class)
                    .slash(identity)
                    .withRel("my-page"));
        }
    }

    public static List<MemberSuspensionDto> findActivateSuspensions(List<MemberSuspensionDto> suspensions){
        return Optional.of(suspensions)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(suspension -> suspension.getSuspendedTo().isAfter(LocalDateTime.now()))
                .collect(Collectors.toUnmodifiableList());
    }
}
