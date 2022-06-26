package com.choimory.itemvaluechecker.api.userapi.member.code;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.springframework.data.domain.Sort;

@AllArgsConstructor
@Getter
public enum MemberDefaultSort {
    VIEWS("createdAt", Sort.Direction.DESC);

    private final String property;
    private final Sort.Direction direction;
}
