package com.choimory.itemvaluechecker.api.userapi.user.dto.request;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.user.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserAuthority;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserSuspension;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.regex.Pattern;

@Builder
@RequiredArgsConstructor
@Getter
public class UserJoinRequest {
    @AllArgsConstructor
    @Getter
    public enum UserJoinRequestValidate {
        ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED(1, "ID에 영문, 숫자 외 문자가 포함되어 있습니다"),
        ID_LENGTH_NOT_VALID(2, "ID 글자수가 5글자 미만이거나 10글자를 초과하였습니다"),
        PASSWORD_LENGTH_NOT_VALID(3, "비밀번호 글자수가 8글자 미만이거나 20글자를 초과하였습니다"),
        PASSWORD_NOT_CONTAINS_SPECIAL_CHARACTER(4, "비밀번호에 특문이 포함되지 않았습니다"),
        PASSWORD_NOT_CONTAINS_UPPER_AND_LOWER_CASE(5, "비밀번호에 대소문자가 포함되지 않았습니다"),
        EMAIL_PATTERN_NOT_VALID(5, "이메일 형식이 부적합합니다"),
        ID_DUPLICATE(6, "이미 가입된 아이디입니다"),
        ID_MISSING(7, "아이디를 입력해주세요"),
        PASSWORD_MISSING(8, "비밀번호를 입력해주세요"),
        NAME_MISSING(9, "이름을 입력해주세요");

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

    public void requiredArgsValidate() throws CommonException{
        /*아이디*/
        if(!StringUtils.hasText(id)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequestValidate.ID_MISSING.getCode(),
                    UserJoinRequestValidate.ID_MISSING.getMessage());
        }

        /*비밀번호*/
        if(!StringUtils.hasText(password)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequestValidate.PASSWORD_MISSING.getCode(),
                    UserJoinRequestValidate.PASSWORD_MISSING.getMessage());
        }

        /*이름*/
        if(!StringUtils.hasText(name)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequestValidate.NAME_MISSING.getCode(),
                    UserJoinRequestValidate.NAME_MISSING.getMessage());
        }
    }

    public void isIdValidate() throws CommonException{
        /*영문, 숫자 외 문자 포함 여부*/
        if(!isIdContainsEnglishAndNumberOnly()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequestValidate.ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED.getCode(),
                    UserJoinRequestValidate.ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED.getMessage());
        }

        /*아이디 길이 확인*/
        if(!isIdLengthValidate(5, 15)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequestValidate.ID_LENGTH_NOT_VALID.getCode(),
                    UserJoinRequestValidate.ID_LENGTH_NOT_VALID.getMessage());
        }
    }

    public void isPasswordValidate() throws CommonException{
        /*비밀번호 길이 확인*/
        if(!isPasswordLengthValidate(8, 20)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequestValidate.PASSWORD_LENGTH_NOT_VALID.getCode(),
                    UserJoinRequestValidate.PASSWORD_LENGTH_NOT_VALID.getMessage());
        }

        /*특문 포함 여부*/
        if(!isPasswordContainsSpecialCharacter()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequestValidate.PASSWORD_NOT_CONTAINS_SPECIAL_CHARACTER.getCode(),
                    UserJoinRequestValidate.PASSWORD_NOT_CONTAINS_SPECIAL_CHARACTER.getMessage());
        }

        /*대소문자 포함 여부*/
        if(!isPasswordContainsUpperAndLowerCase()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequestValidate.PASSWORD_NOT_CONTAINS_UPPER_AND_LOWER_CASE.getCode(),
                    UserJoinRequestValidate.PASSWORD_NOT_CONTAINS_UPPER_AND_LOWER_CASE.getMessage());
        }
    }

    public void isEmailValidate() throws CommonException {
        /*이메일 형식 준수 여부*/
        if(!isEmailPatternValidate()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequestValidate.EMAIL_PATTERN_NOT_VALID.getCode(),
                    UserJoinRequestValidate.EMAIL_PATTERN_NOT_VALID.getMessage());
        }
    }

    // TODO
    private boolean isIdContainsEnglishAndNumberOnly(){
        String pattern = "[a-zA-Z0-9]*$";
        return Pattern.matches(pattern, id);
    }

    // TODO
    private boolean isIdLengthValidate(int min, int max){
        return min < id.length() && id.length() < max;
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
