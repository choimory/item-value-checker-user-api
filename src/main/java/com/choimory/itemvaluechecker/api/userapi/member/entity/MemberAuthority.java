package com.choimory.itemvaluechecker.api.userapi.member.entity;

import com.choimory.itemvaluechecker.api.userapi.common.entity.CommonDateTimeEntity;
import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class MemberAuthority extends CommonDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Enumerated(EnumType.STRING)
    private AuthLevel authLevel;

    @Builder(toBuilder = true)
    public MemberAuthority(LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt, Long id, Member member, AuthLevel authLevel) {
        super(createdAt, modifiedAt, deletedAt);
        this.id = id;
        this.member = member;
        this.authLevel = authLevel;
    }
}