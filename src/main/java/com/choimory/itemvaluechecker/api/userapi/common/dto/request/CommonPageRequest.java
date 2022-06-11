package com.choimory.itemvaluechecker.api.userapi.common.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;

@Builder
@RequiredArgsConstructor
@Getter
public class CommonPageRequest {
    private final int page;
    private final int size;
    private final String sort;
    private final Sort.Direction direction;
}
