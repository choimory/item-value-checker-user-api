package com.choimory.itemvaluechecker.api.userapi.member.dto.request;

import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberAuthority;
import com.choimory.itemvaluechecker.api.userapi.member.valid.MemberValid;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.*;

@Builder
@RequiredArgsConstructor
@Getter
public class MemberJoinRequest {
    @NotBlank
    @Size(min = MemberValid.MIN_ID_LENGTH,
            max = MemberValid.MAX_ID_LENGTH)
    @Pattern(regexp = MemberValid.ID_PATTERN,
            message = MemberValid.MESSAGE_ID_LENGTH_NOT_VALID)
    private final String identity;
    @NotBlank
    @Size(min = MemberValid.MIN_PASSWORD_LENGTH,
            max = MemberValid.MAX_PASSWORD_LENGTH)
    /*TODO @Pattern(regexp = MemberValid.PATTERN_PASSWORD,
            message = MemberValid.MESSAGE_PASSWORD_PATTERN_NOT_VALID)*/
    private final String password;
    @NotBlank
    private final String nickname;
    @NotBlank
    @Email
    private final String email;
    @NotNull
    private final AuthLevel authLevel;

    public Member toEntity(){
        Member member = Member.builder()
                .identity(identity)
                .password(password)
                .nickname(nickname)
                .email(email)
                .build();

        member.setMemberAuthority(MemberAuthority.builder()
                .member(member)
                .authLevel(authLevel)
                .build());

        return member;
    }

    public Member toEntity(@NotNull PasswordEncoder passwordEncoder){
        Member member = Member.builder()
                .identity(identity)
                .password(passwordEncoder.encode(password))
                .nickname(nickname)
                .email(email)
                .build();

        member.setMemberAuthority(MemberAuthority.builder()
                .member(member)
                .authLevel(authLevel)
                .build());

        return member;
    }
}
