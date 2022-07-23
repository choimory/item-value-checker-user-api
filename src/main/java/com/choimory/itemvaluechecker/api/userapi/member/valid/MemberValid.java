package com.choimory.itemvaluechecker.api.userapi.member.valid;

import com.choimory.itemvaluechecker.api.userapi.member.valid.member.MemberEmailValid;
import com.choimory.itemvaluechecker.api.userapi.member.valid.member.MemberIdentityValid;
import com.choimory.itemvaluechecker.api.userapi.member.valid.member.MemberNameValid;
import com.choimory.itemvaluechecker.api.userapi.member.valid.member.MemberPasswordValid;

public interface MemberValid extends MemberIdentityValid, MemberPasswordValid, MemberEmailValid, MemberNameValid {
}
