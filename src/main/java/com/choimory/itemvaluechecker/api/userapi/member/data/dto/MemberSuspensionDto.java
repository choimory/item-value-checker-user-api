package com.choimory.itemvaluechecker.api.userapi.member.data.dto;

import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberSuspension;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberSuspensionDto {
    private String reason;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime suspendedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime suspendedTo;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime createdAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime modifiedAt;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "yyyy-MM-dd HH:mm:ss", timezone = "Asia/Seoul")
    private LocalDateTime deletedAt;

    public static MemberSuspensionDto toDto(MemberSuspension memberSuspension){
        return memberSuspension == null
                ? null
                : MemberSuspensionDto.builder()
                .reason(memberSuspension.getReason())
                .suspendedAt(memberSuspension.getSuspendedAt())
                .suspendedTo(memberSuspension.getSuspendedTo())
                .createdAt(memberSuspension.getCreatedAt())
                .modifiedAt(memberSuspension.getModifiedAt())
                .deletedAt(memberSuspension.getDeletedAt())
                .build();
    }
}
