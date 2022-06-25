package com.choimory.itemvaluechecker.api.userapi.member.entity;

import com.choimory.itemvaluechecker.api.userapi.common.entity.CommonDateTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class MemberSuspension extends CommonDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    @ManyToOne
    @JoinColumn(name = "member_idx")
    private Member member;
    private String reason;
    private LocalDateTime suspendedAt;
    private LocalDateTime suspendedTo;
}
