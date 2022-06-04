package com.choimory.itemvaluechecker.api.userapi.user.dto.request;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.springframework.http.HttpStatus;

import java.util.stream.Stream;

class UserJoinRequestTest {

    @ParameterizedTest
    @MethodSource("requiredArgsValidateMethodSource")
    @DisplayName("필수값 검증 테스트")
    void requiredArgsValidate(boolean isSuccess, HttpStatus status, String id, String password, String name, UserJoinRequest.UserJoinRequestValidate validate) {
        /*given*/
        UserJoinRequest request = UserJoinRequest.builder()
                .id(id)
                .password(password)
                .name(name)
                .build();

        try {
            /*when*/
            request.requiredArgsValidate();

            /*then*/
            Assertions.assertThat(isSuccess).isEqualTo(true);
        } catch (CommonException e) {
            /*then*/
            Assertions.assertThat(isSuccess).isEqualTo(false);
            Assertions.assertThat(e.getStatus()).isEqualTo(status);
            Assertions.assertThat(e.getCode()).isEqualTo(validate.getCode());
            Assertions.assertThat(e.getMessage()).isEqualTo(validate.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("isIdValidateMethodSource")
    @DisplayName("아이디 검증 테스트")
    void isIdValidate(boolean isSuccess, HttpStatus status, String id, UserJoinRequest.UserJoinRequestValidate validate) {
        /*given*/
        UserJoinRequest request = UserJoinRequest.builder()
                .id(id)
                .build();
        try {
            /*when*/
            request.isIdValidate();

            /*then*/
            Assertions.assertThat(isSuccess).isEqualTo(true);
        } catch (CommonException e) {
            /*then*/
            Assertions.assertThat(isSuccess).isEqualTo(false);
            Assertions.assertThat(e.getStatus()).isEqualTo(status);
            Assertions.assertThat(e.getCode()).isEqualTo(validate.getCode());
            Assertions.assertThat(e.getMessage()).isEqualTo(validate.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("isPasswordValidateMethodSource")
    @DisplayName("비밀번호 검증 테스트")
    void isPasswordValidate(boolean isSuccess, HttpStatus status, String password, UserJoinRequest.UserJoinRequestValidate validate) {
        /*given*/
        UserJoinRequest request = UserJoinRequest.builder()
                .password(password)
                .build();

        try {
            /*when*/
            request.isPasswordValidate();

            /*then*/
            Assertions.assertThat(isSuccess).isEqualTo(true);
        } catch (CommonException e) {
            /*then*/
            Assertions.assertThat(isSuccess).isEqualTo(false);
            Assertions.assertThat(e.getStatus()).isEqualTo(status);
            Assertions.assertThat(e.getCode()).isEqualTo(validate.getCode());
            Assertions.assertThat(e.getMessage()).isEqualTo(validate.getMessage());
        }
    }

    @ParameterizedTest
    @MethodSource("isEmailValidateMethodSource")
    @DisplayName("이메일 검증 테스트")
    void isEmailValidate(boolean isSuccess, HttpStatus status, String email, UserJoinRequest.UserJoinRequestValidate validate) {
        /*given*/
        UserJoinRequest request = UserJoinRequest.builder()
                .email(email)
                .build();

        try {
            /*when*/
            request.isEmailValidate();

            /*then*/
            Assertions.assertThat(isSuccess).isEqualTo(true);
        } catch (CommonException e) {
            /*then*/
            Assertions.assertThat(isSuccess).isEqualTo(false);
            Assertions.assertThat(e.getStatus()).isEqualTo(status);
            Assertions.assertThat(e.getCode()).isEqualTo(validate.getCode());
            Assertions.assertThat(e.getMessage()).isEqualTo(validate.getMessage());
        }
    }

    static Stream<Arguments> requiredArgsValidateMethodSource() {
        return Stream.<Arguments>builder()
                /*성공*/
                .add(Arguments.arguments(true, null, "id", "password", "name", null))
                /*아이디 누락*/
                .add(Arguments.arguments(false, HttpStatus.BAD_REQUEST, "", "password", "name", UserJoinRequest.UserJoinRequestValidate.ID_MISSING))
                /*비밀번호 누락*/
                .add(Arguments.arguments(false, HttpStatus.BAD_REQUEST, "id", "", "name", UserJoinRequest.UserJoinRequestValidate.PASSWORD_MISSING))
                /*이름 누락*/
                .add(Arguments.arguments(false, HttpStatus.BAD_REQUEST, "id", "password", "", UserJoinRequest.UserJoinRequestValidate.NAME_MISSING))
                .build();
    }

    static Stream<Arguments> isIdValidateMethodSource() {
        return Stream.<Arguments>builder()
                /*성공*/
                .add(Arguments.arguments(true, null, "choimory99", null))
                /*영문, 숫자 외 문자 포함*/
                .add(Arguments.arguments(false, HttpStatus.BAD_REQUEST, "sdfsdf@#$@#$%%#@", UserJoinRequest.UserJoinRequestValidate.ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED)) // 특문
                .add(Arguments.arguments(false, HttpStatus.BAD_REQUEST, "sdweff한글한글한글", UserJoinRequest.UserJoinRequestValidate.ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED)) // 한글
                .add(Arguments.arguments(false, HttpStatus.BAD_REQUEST, "s p a c e", UserJoinRequest.UserJoinRequestValidate.ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED)) // 띄어쓰기
                /*5글자 미만 혹은 15글자 초과*/
                .add(Arguments.arguments(false, HttpStatus.BAD_REQUEST, "id", UserJoinRequest.UserJoinRequestValidate.ID_LENGTH_NOT_VALID)) // 5글자 미만
                .add(Arguments.arguments(false, HttpStatus.BAD_REQUEST, "idididididididididididd", UserJoinRequest.UserJoinRequestValidate.ID_LENGTH_NOT_VALID)) // 15글자 초과
                .build();
    }

    static Stream<Arguments> isPasswordValidateMethodSource() {
        return Stream.<Arguments>builder()
                /*성공*/
                .add(Arguments.arguments(true, null, null, null))
                /*비밀번호 길이 확인*/
                /*특문 포함 여부*/
                /*대소문자 포함 여부*/
                .build();
    }

    static Stream<Arguments> isEmailValidateMethodSource() {
        return Stream.<Arguments>builder()
                /*성공*/
                .add(Arguments.arguments(true, null, null, null))
                /*이메일 형식 준수 여부*/
                .build();
    }
}