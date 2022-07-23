package com.choimory.itemvaluechecker.api.userapi.member.dto.request;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.stream.Stream;

class MemberJoinRequestTest {
    @ParameterizedTest
    @MethodSource("toEntityMethodSource")
    @DisplayName("요청객체를 엔티티로 변환할때 암호화 테스트")
    void toEntityParameterizedTest(boolean isSuccess, String password) {
        /*given*/
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        MemberJoinRequest param = MemberJoinRequest.builder()
                .password(password)
                .build();

        /*when*/
        Member entity = param.toEntity(passwordEncoder);

        /*then*/
        Assertions.assertThat(isSuccess).isEqualTo(passwordEncoder.matches(password, entity.getPassword()));
    }

    static Stream<Arguments> toEntityMethodSource(){
        return Stream.<Arguments>builder()
                .add(Arguments.of(true, "Asdqwe123!"))
                .build();
    }
}