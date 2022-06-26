package com.choimory.itemvaluechecker.api.userapi.member.dto.response;

import com.choimory.itemvaluechecker.api.userapi.member.controller.MemberController;
import com.choimory.itemvaluechecker.api.userapi.member.dto.dto.MemberDto;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;

import java.util.List;

@Getter
public class MemberListResponse extends RepresentationModel {
    private final int page;
    private final int size;
    private final String sort;
    private final long totalCount;
    private final int totalPage;
    private final List<MemberDto> members;

    @Builder
    public MemberListResponse(int page, int size, String sort, long totalCount, int totalPage, List<MemberDto> members) {
        this.page = page;
        this.size = size;
        this.sort = sort;
        this.totalCount = totalCount;
        this.totalPage = totalPage;
        this.members = members;
        add(WebMvcLinkBuilder.linkTo(MemberController.class).withSelfRel());
    }
}
