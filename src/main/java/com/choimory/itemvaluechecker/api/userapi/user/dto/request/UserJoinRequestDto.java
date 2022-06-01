package com.choimory.itemvaluechecker.api.userapi.user.dto.request;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.user.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserAuthority;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserSuspension;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

import java.time.LocalDateTime;
import java.util.Optional;

@RequiredArgsConstructor
@Getter
public class UserJoinRequestDto {
    @AllArgsConstructor
    @Getter
    public enum JoinValidate {
        ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED(1, "ID에 영문, 숫자 외 문자가 포함되어 있습니다"),
        ID_LENGTH_NOT_VALID(2, "ID 글자수가 5글자 미만이거나 10글자를 초과하였습니다"),
        PASSWORD_LENGTH_NOT_VALID(3, "비밀번호 글자수가 8글자 미만이거나 20글자를 초과하였습니다"),
        PASSWORD_NOT_CONTAINS_SPECIAL_CHARACTER(4, "비밀번호에 특문이 포함되지 않았습니다"),
        PASSWORD_NOT_CONTAINS_UPPER_AND_LOWER_CASE(5, "비밀번호에 대소문자가 포함되지 않았습니다"),
        EMAIL_PATTERN_NOT_VALID(5, "이메일 형식이 부적합합니다"),
        ID_DUPLICATE(6, "이미 가입된 아이디입니다");

        private final int code;
        private final String message;
    }

    private final String id;
    private final String password;
    private final String name;
    private final String email;
    private final AuthLevel authLevel;

    public User toEntity(){
        return User.builder()
                .id(id)
                .password(password)
                .name(name)
                .email(email)
                .createdTime(LocalDateTime.now())
                .modifiedTime(null)
                .deletedTime(null)
                .userAuthority(UserAuthority.builder()
                        .id(id)
                        .authLevel(authLevel)
                        .build())
                .userSuspension(UserSuspension.builder()
                        .id(id)
                        .isSuspended(0)
                        .suspendedTime(null)
                        .reason(null)
                        .build())
                .build();
    }

    // TODO
    public void requiredArgsValidate() throws Exception {

    }

    public void isIdValidate() throws Exception {
        /*영문, 숫자 외 문자 포함 여부*/
        if(!isIdContainsEnglishAndNumberOnly()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    JoinValidate.ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED.getCode(),
                    JoinValidate.ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED.getMessage());
        }

        /*10글자 초과 여부*/
        if(!isIdLengthValidate(10)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    JoinValidate.ID_LENGTH_NOT_VALID.getCode(),
                    JoinValidate.ID_LENGTH_NOT_VALID.getMessage());
        }
    }

    public void isPasswordValidate() throws Exception {
        /*비밀번호 길이 확인*/
        if(!isPasswordLengthValidate(8, 20)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    JoinValidate.PASSWORD_LENGTH_NOT_VALID.getCode(),
                    JoinValidate.PASSWORD_LENGTH_NOT_VALID.getMessage());
        }

        /*특문 포함 여부*/
        if(!isPasswordContainsSpecialCharacter()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    JoinValidate.PASSWORD_NOT_CONTAINS_SPECIAL_CHARACTER.getCode(),
                    JoinValidate.PASSWORD_NOT_CONTAINS_SPECIAL_CHARACTER.getMessage());
        }

        /*대소문자 포함 여부*/
        if(!isPasswordContainsUpperAndLowerCase()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    JoinValidate.PASSWORD_NOT_CONTAINS_UPPER_AND_LOWER_CASE.getCode(),
                    JoinValidate.PASSWORD_NOT_CONTAINS_UPPER_AND_LOWER_CASE.getMessage());
        }
    }

    public void isEmailValidate() throws Exception {
        /*이메일 형식 준수 여부*/
        if(!isEmailPatternValidate()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    JoinValidate.EMAIL_PATTERN_NOT_VALID.getCode(),
                    JoinValidate.EMAIL_PATTERN_NOT_VALID.getMessage());
        }
    }

    // TODO
    private boolean isIdContainsEnglishAndNumberOnly(){
        return true;
    }

    // TODO
    private boolean isIdLengthValidate(int length){
        return true;
    }

    // TODO
    private boolean isPasswordLengthValidate(int min, int max){
        return true;
    }

    // TODO
    private boolean isPasswordContainsUpperAndLowerCase(){
        return true;
    }

    // TODO
    private boolean isPasswordContainsSpecialCharacter(){
        return true;
    }

    // TODO
    private boolean isEmailPatternValidate(){
        return true;
    }
}
