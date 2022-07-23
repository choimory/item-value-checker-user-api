package com.choimory.itemvaluechecker.api.userapi.member.valid.member;

public interface MemberPasswordValid {
    String MESSAGE_PASSWORD_PATTERN_NOT_VALID = "비밀번호 형식이 부적합합니다";
    String MESSAGE_PASSWORD_LENGTH_NOT_VALID = "비밀번호 글자수가 8글자 미만이거나 20글자를 초과하였습니다";
    String MESSAGE_PASSWORD_MISSING = "비밀번호를 입력해주세요";
    String MESSAGE_PASSWORD_NOT_CONTAINS_NUMBER = "비밀번호에 숫자가 포함되지 않았습니다";

    int MIN_PASSWORD_LENGTH = 8;
    int MAX_PASSWORD_LENGTH = 20;

    String PATTERN_PASSWORD = "[^a-zA-Z0-9 가-힣ㄱ-ㅋㅏ-ㅣ]";
    String PATTERN_CONTAINS_SPECIAL_CHARACTER = "[^a-z0-9 가-힣ㄱ-ㅋㅏ-ㅣ]";
}
