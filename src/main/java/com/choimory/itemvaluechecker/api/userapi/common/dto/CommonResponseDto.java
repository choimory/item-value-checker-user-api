package com.choimory.itemvaluechecker.api.userapi.common.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Builder
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class CommonResponseDto<T> {
    @Builder.Default
    private int status = 0;
    private String message;
    private T data;
}
