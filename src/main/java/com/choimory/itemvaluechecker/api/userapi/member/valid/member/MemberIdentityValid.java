package com.choimory.itemvaluechecker.api.userapi.member.valid.member;

public interface MemberIdentityValid {
    int CODE_ID_DUPLICATE = 6;

    String MESSAGE_ID_LENGTH_NOT_VALID = "ID 글자수가 5글자 미만이거나 10글자를 초과하였습니다";
    String MESSAGE_ID_DUPLICATE =  "이미 가입된 아이디입니다";
    String MESSAGE_ID_NOT_ENGLISH_AND_NUMBERS_CONTAINED = "ID에 영문, 숫자 외 문자가 포함되어 있습니다";
    String MESSAGE_ID_MISSING = "아이디를 입력해주세요";

    int MIN_ID_LENGTH = 5;
    int MAX_ID_LENGTH = 15;

    String ID_PATTERN = "[a-zA-Z0-9]*$";
}
