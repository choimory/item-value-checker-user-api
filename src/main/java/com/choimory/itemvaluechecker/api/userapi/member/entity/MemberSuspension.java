package com.choimory.itemvaluechecker.api.userapi.member.entity;

import com.choimory.itemvaluechecker.api.userapi.common.entity.CommonDateTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class MemberSuspension extends CommonDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    private String reason;
    private LocalDateTime suspendedAt;
    private LocalDateTime suspendedTo;

    @Builder(toBuilder = true)
    public MemberSuspension(LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt, Long id, Member member, String reason, LocalDateTime suspendedAt, LocalDateTime suspendedTo) {
        super(createdAt, modifiedAt, deletedAt);
        this.id = id;
        this.member = member;
        this.reason = reason;
        this.suspendedAt = suspendedAt;
        this.suspendedTo = suspendedTo;
    }
}
