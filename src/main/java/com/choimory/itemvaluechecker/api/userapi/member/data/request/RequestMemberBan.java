package com.choimory.itemvaluechecker.api.userapi.member.data.request;

import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSuspension;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Builder
@RequiredArgsConstructor
@Getter
public class RequestMemberBan {
    @NotNull
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd'T'HH:mm:ss")
    private final LocalDateTime suspendTo;
    @NotEmpty
    private final String reason;

    public static boolean isSuspendActivated(List<MemberSuspension> suspensions){
        return !Optional.of(suspensions)
                .orElseGet(Collections::emptyList)
                .stream()
                .filter(suspension -> suspension.getSuspendedTo().isAfter(LocalDateTime.now()))
                .collect(Collectors.toUnmodifiableList())
                .isEmpty();
    }
}
