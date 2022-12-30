package com.choimory.itemvaluechecker.api.userapi.member.controller;

import com.choimory.itemvaluechecker.api.userapi.common.dto.request.CommonPageRequest;
import com.choimory.itemvaluechecker.api.userapi.member.code.MemberDefaultSort;
import com.choimory.itemvaluechecker.api.userapi.member.data.request.RequestMemberBan;
import com.choimory.itemvaluechecker.api.userapi.member.data.request.RequestMemberFindAll;
import com.choimory.itemvaluechecker.api.userapi.member.data.request.RequestMemberJoin;
import com.choimory.itemvaluechecker.api.userapi.member.data.request.RequestMemberLogin;
import com.choimory.itemvaluechecker.api.userapi.member.data.response.*;
import com.choimory.itemvaluechecker.api.userapi.member.service.MemberService;
import com.choimory.itemvaluechecker.api.userapi.member.valid.MemberValid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.validation.constraints.Size;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
@Validated
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{identity}")
    public ResponseMemberFind find(@PathVariable
                                   @Valid
                                   @Size(min = MemberValid.MIN_ID_LENGTH,
                                           max = MemberValid.MAX_ID_LENGTH)
                                   final String identity){
        return memberService.find(identity);
    }

    @GetMapping
    public ResponseMemberFindAll findAll(final RequestMemberFindAll param,
                                         final CommonPageRequest commonPageRequest){
        return memberService.findAll(param, commonPageRequest.of(MemberDefaultSort.VIEWS.getProperty(), MemberDefaultSort.VIEWS.getDirection()));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMemberJoin join(@RequestBody
                                   @Valid
                                   final RequestMemberJoin param) {
        return memberService.join(param);
    }

    @PostMapping("/login")
    public ResponseMemberLogin login(@RequestBody @Valid final RequestMemberLogin param){
        return memberService.login(param);
    }

    public ResponseMemberLogout logout(){
        return null;
    }

    public ResponseMemberUpdate update(){
        return null;
    }

    @PatchMapping("/ban/{identity}")
    public ResponseMemberBan ban(@PathVariable
                                 @Valid
                                 @Size(min = MemberValid.MIN_ID_LENGTH,
                                         max = MemberValid.MAX_ID_LENGTH)
                                 final String identity,
                                 @RequestBody
                                 @Valid
                                 final RequestMemberBan param){
        return memberService.ban(identity, param);
    }
}
