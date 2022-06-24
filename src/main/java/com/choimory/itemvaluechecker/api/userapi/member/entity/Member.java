package com.choimory.itemvaluechecker.api.userapi.member.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Member {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idx;
    private String id;
    private String password;
    private String name;
    private String email;
    private LocalDateTime createdAt;
    private LocalDateTime modifiedAt;
    private LocalDateTime deletedAt;
    @OneToMany(mappedBy = "member")
    private List<MemberSocial> memberSocials = new ArrayList<>();
    @OneToOne(mappedBy = "member")
    private MemberAuthority memberAuthority;
    @OneToMany(mappedBy = "member", fetch = FetchType.LAZY)
    private List<MemberSuspension> memberSuspensions = new ArrayList<>();
}