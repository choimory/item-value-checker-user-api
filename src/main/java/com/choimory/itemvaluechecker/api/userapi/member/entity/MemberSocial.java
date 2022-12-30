package com.choimory.itemvaluechecker.api.userapi.member.entity;

import com.choimory.itemvaluechecker.api.userapi.common.entity.CommonDateTimeEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@NoArgsConstructor
@Getter
public class MemberSocial extends CommonDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;
    @Enumerated(EnumType.STRING)
    private SocialType socialType;
    private String socialId;

    @Builder(toBuilder = true)
    public MemberSocial(LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt, Long id, Member member, SocialType socialType, String socialId) {
        super(createdAt, modifiedAt, deletedAt);
        this.id = id;
        this.member = member;
        this.socialType = socialType;
        this.socialId = socialId;
    }

    @AllArgsConstructor
    @Getter
    public enum SocialType {
        KAKAO,
        NAVER,
        GOOGLE
    }
}
