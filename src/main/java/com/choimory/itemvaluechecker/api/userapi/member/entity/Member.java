package com.choimory.itemvaluechecker.api.userapi.member.entity;

import com.choimory.itemvaluechecker.api.userapi.common.entity.CommonDateTimeEntity;
import lombok.*;
import lombok.experimental.SuperBuilder;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member extends CommonDateTimeEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String memberId;
    private String password;
    private String name;
    private String email;
    @Builder.Default
    @OneToMany(mappedBy = "member")
    private List<MemberSocial> memberSocials = new ArrayList<>();
    @OneToOne(mappedBy = "member")
    private MemberAuthority memberAuthority;
    @Builder.Default
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberSuspension> memberSuspensions = new ArrayList<>();
}