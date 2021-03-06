package com.choimory.itemvaluechecker.api.userapi.common.dto.response;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Builder
@Getter
@RequiredArgsConstructor
public class CommonResponse<T> {
    @Builder.Default
    private final int status = 0;
    private final String message;
    private final T data;
}
