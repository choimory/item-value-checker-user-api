package com.choimory.itemvaluechecker.api.userapi.member.controller;

import com.choimory.itemvaluechecker.api.userapi.common.dto.request.CommonPageRequest;
import com.choimory.itemvaluechecker.api.userapi.member.code.MemberDefaultSort;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.RequestMemberJoin;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.RequestMemberList;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.*;
import com.choimory.itemvaluechecker.api.userapi.member.service.MemberService;
import com.choimory.itemvaluechecker.api.userapi.member.valid.MemberValid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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
    public ResponseMemberView view(@PathVariable
                                       @Valid
                                       @Size(min = MemberValid.MIN_ID_LENGTH,
                                               max = MemberValid.MAX_ID_LENGTH)
                                       final String identity){
        return memberService.view(identity);
    }

    @GetMapping
    public ResponseMemberList views(final RequestMemberList param,
                                    final CommonPageRequest commonPageRequest){
        return memberService.views(param, commonPageRequest.of(MemberDefaultSort.VIEWS.getProperty(), MemberDefaultSort.VIEWS.getDirection()));
    }

    @PutMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ResponseMemberJoin join(@RequestBody
                                       @Valid
                                       final RequestMemberJoin param) throws Exception {
        return memberService.join(param);
    }

    @PostMapping("/login")
    public ResponseMemberLogin login(){
        return null;
    }

    public ResponseEntity<ResponseMemberLogout> logout(){
        return null;
    }

    public ResponseEntity<ResponseMemberUpdate> update(){
        return null;
    }

    public ResponseEntity<ResponseMemberBan> ban(){
        return null;
    }
}
