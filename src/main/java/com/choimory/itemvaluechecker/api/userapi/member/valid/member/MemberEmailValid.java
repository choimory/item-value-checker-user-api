package com.choimory.itemvaluechecker.api.userapi.member.valid.member;

public interface MemberEmailValid {
    String MESSAGE_EMAIL_PATTERN_NOT_VALID = "이메일 형식이 부적합합니다";

    String PATTERN_EMAIL = "^[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_.]?[0-9a-zA-Z])*.[a-zA-Z]{2,3}$";
}
