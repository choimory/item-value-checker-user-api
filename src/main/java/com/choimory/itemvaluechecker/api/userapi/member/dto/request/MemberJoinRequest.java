package com.choimory.itemvaluechecker.api.userapi.member.dto.request;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.member.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.member.entity.Member;
import com.choimory.itemvaluechecker.api.userapi.member.entity.MemberAuthority;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.util.StringUtils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Builder
@RequiredArgsConstructor
@Getter
public class MemberJoinRequest {
    @AllArgsConstructor
    @Getter
    public enum MemberJoinRequestValidate {
        ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED(1, "ID에 영문, 숫자 외 문자가 포함되어 있습니다"),
        ID_LENGTH_NOT_VALID(2, "ID 글자수가 5글자 미만이거나 10글자를 초과하였습니다"),
        PASSWORD_LENGTH_NOT_VALID(3, "비밀번호 글자수가 8글자 미만이거나 20글자를 초과하였습니다"),
        PASSWORD_NOT_CONTAINS_SPECIAL_CHARACTER(4, "비밀번호에 특문이 포함되지 않았습니다"),
        PASSWORD_NOT_CONTAINS_UPPER_AND_LOWER_CASE(5, "비밀번호에 대소문자가 포함되지 않았습니다"),
        EMAIL_PATTERN_NOT_VALID(5, "이메일 형식이 부적합합니다"),
        ID_DUPLICATE(6, "이미 가입된 아이디입니다"),
        ID_MISSING(7, "아이디를 입력해주세요"),
        PASSWORD_MISSING(8, "비밀번호를 입력해주세요"),
        NAME_MISSING(9, "이름을 입력해주세요"),
        PASSWORD_NOT_CONTAINS_NUMBER(10, "비밀번호에 숫자가 포함되지 않았습니다");

        private final int code;
        private final String message;
    }

    private final String idName;
    private final String password;
    private final String nickname;
    private final String email;
    private final AuthLevel authLevel;

    public Member toEntity(){
        Member member = Member.builder()
                .idName(idName)
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

    public void requiredArgsValidate() throws CommonException{
        /*아이디*/
        if(!StringUtils.hasText(idName)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.ID_MISSING.getCode(),
                    MemberJoinRequestValidate.ID_MISSING.getMessage());
        }

        /*비밀번호*/
        if(!StringUtils.hasText(password)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.PASSWORD_MISSING.getCode(),
                    MemberJoinRequestValidate.PASSWORD_MISSING.getMessage());
        }

        /*이름*/
        if(!StringUtils.hasText(nickname)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.NAME_MISSING.getCode(),
                    MemberJoinRequestValidate.NAME_MISSING.getMessage());
        }
    }

    public void isIdValidate() throws CommonException{
        /*영문, 숫자 외 문자 포함 여부*/
        if(!isIdContainsEnglishAndNumberOnly()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED.getCode(),
                    MemberJoinRequestValidate.ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED.getMessage());
        }

        /*아이디 길이 확인*/
        if(!isIdLengthValidate(5, 15)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.ID_LENGTH_NOT_VALID.getCode(),
                    MemberJoinRequestValidate.ID_LENGTH_NOT_VALID.getMessage());
        }
    }

    public void isPasswordValidate() throws CommonException{
        /*비밀번호 길이 확인*/
        if(!isPasswordLengthValidate(8, 20)){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.PASSWORD_LENGTH_NOT_VALID.getCode(),
                    MemberJoinRequestValidate.PASSWORD_LENGTH_NOT_VALID.getMessage());
        }

        /*숫자 포함 여부*/
        if(!isPasswordContainsNumber()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.PASSWORD_NOT_CONTAINS_NUMBER.getCode(),
                    MemberJoinRequestValidate.PASSWORD_NOT_CONTAINS_NUMBER.getMessage());
        }

        /*특문 포함 여부*/
        if(!isPasswordContainsSpecialCharacter()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.PASSWORD_NOT_CONTAINS_SPECIAL_CHARACTER.getCode(),
                    MemberJoinRequestValidate.PASSWORD_NOT_CONTAINS_SPECIAL_CHARACTER.getMessage());
        }

        /*대소문자 포함 여부*/
        if(!isPasswordContainsUpperAndLowerCase()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.PASSWORD_NOT_CONTAINS_UPPER_AND_LOWER_CASE.getCode(),
                    MemberJoinRequestValidate.PASSWORD_NOT_CONTAINS_UPPER_AND_LOWER_CASE.getMessage());
        }
    }

    public void isEmailValidate() throws CommonException {
        /*이메일 형식 준수 여부*/
        if(!isEmailPatternValidate()){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    MemberJoinRequestValidate.EMAIL_PATTERN_NOT_VALID.getCode(),
                    MemberJoinRequestValidate.EMAIL_PATTERN_NOT_VALID.getMessage());
        }
    }

    private boolean isIdContainsEnglishAndNumberOnly(){
        String pattern = "[a-zA-Z0-9]*$";

        return Pattern.matches(pattern, idName);
    }

    private boolean isIdLengthValidate(int min, int max){
        return min < idName.length() && idName.length() < max;
    }

    private boolean isPasswordLengthValidate(int min, int max){
        return min < password.length() && password.length( ) < max;
    }

    private boolean isPasswordContainsNumber(){
        for (char c : password.toCharArray()) {
            if(Character.isDigit(c)){
                return true;
            }
        }

        return false;
    }

    private boolean isPasswordContainsUpperAndLowerCase(){
        boolean isContainUpperCase = false;
        boolean isContainLowerCase = false;

        for (char c : password.toCharArray()) {
            if(Character.isUpperCase(c)){
                isContainUpperCase = true;
            }
            if(Character.isLowerCase(c)){
                isContainLowerCase = true;
            }
        }

        return isContainUpperCase && isContainLowerCase;
    }

    private boolean isPasswordContainsSpecialCharacter(){
        Pattern p = Pattern.compile("[^a-z0-9 가-힣ㄱ-ㅋㅏ-ㅣ]", Pattern.CASE_INSENSITIVE);
        Matcher m = p.matcher(password);
        return m.find();
    }

    private boolean isEmailPatternValidate(){
        String pattern = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
        return Pattern.matches(pattern, email);
    }
}
