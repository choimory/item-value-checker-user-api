package com.choimory.itemvaluechecker.api.userapi.member.entity;

import com.choimory.itemvaluechecker.api.userapi.common.entity.CommonDateTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
public class Member extends CommonDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String identity;
    private String password;
    private String nickname;
    private String email;
    @OneToOne(mappedBy = "member", cascade = CascadeType.ALL, orphanRemoval = true)
    private MemberAuthority memberAuthority;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, orphanRemoval = true)
    private List<MemberSocial> memberSocials = new ArrayList<>();
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY, cascade = CascadeType.PERSIST, orphanRemoval = true)
    private List<MemberSuspension> memberSuspensions = new ArrayList<>();

    @Builder(toBuilder = true)
    public Member(LocalDateTime createdAt, LocalDateTime modifiedAt, LocalDateTime deletedAt, Long id, String identity, String password, String nickname, String email, MemberAuthority memberAuthority, List<MemberSocial> memberSocials, List<MemberSuspension> memberSuspensions) {
        super(createdAt, modifiedAt, deletedAt);
        this.id = id;
        this.identity = identity;
        this.password = password;
        this.nickname = nickname;
        this.email = email;
        this.memberAuthority = memberAuthority;
        this.memberSocials = memberSocials;
        this.memberSuspensions = memberSuspensions;
    }

    public Member join(MemberAuthority memberAuthority){
        this.memberAuthority = memberAuthority;
        return this;
    }
}