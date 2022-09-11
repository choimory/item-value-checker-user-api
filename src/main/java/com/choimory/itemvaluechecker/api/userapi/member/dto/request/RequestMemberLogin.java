package com.choimory.itemvaluechecker.api.userapi.member.dto.request;

import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

@Builder
@RequiredArgsConstructor
@Getter
public class RequestMemberLogin {
    @NotEmpty
    @NotBlank
    private final String identity;
    @NotEmpty
    @NotBlank
    private final String password;

    public Boolean isPasswordMatched(PasswordEncoder passwordEncoder, String encodedPassword){
        return passwordEncoder == null
                ? null
                : passwordEncoder.matches(this.getPassword(), encodedPassword);
    }
}
