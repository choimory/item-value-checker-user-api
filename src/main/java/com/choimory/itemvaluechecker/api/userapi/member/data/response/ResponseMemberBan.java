package com.choimory.itemvaluechecker.api.userapi.member.data.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.hateoas.RepresentationModel;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class ResponseMemberBan extends RepresentationModel<ResponseMemberBan> {
    private String identity;
    private LocalDateTime suspendedAt;
    private LocalDateTime suspendedTo;
    private String reason;
}
