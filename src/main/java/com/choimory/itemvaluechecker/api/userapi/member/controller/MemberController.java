package com.choimory.itemvaluechecker.api.userapi.member.controller;

import com.choimory.itemvaluechecker.api.userapi.common.dto.request.CommonPageRequest;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberJoinRequest;
import com.choimory.itemvaluechecker.api.userapi.member.dto.request.MemberListRequest;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberBanResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberJoinResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberListResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberLoginResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberLogoutResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberUpdateResponse;
import com.choimory.itemvaluechecker.api.userapi.member.dto.response.MemberViewResponse;
import com.choimory.itemvaluechecker.api.userapi.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/member")
public class MemberController {
    private final MemberService memberService;

    @GetMapping("/{id}")
    public ResponseEntity<MemberViewResponse> view(@PathVariable final String id){
        return new ResponseEntity<>(memberService.view(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<MemberListResponse> views(final MemberListRequest param,
                                                    final CommonPageRequest commonPageRequest){
        return new ResponseEntity<>(memberService.views(param, Pageable.ofSize(10)), HttpStatus.OK);
    }

    @PutMapping("/join")
    public ResponseEntity<MemberJoinResponse> join(@RequestBody(required = false) final MemberJoinRequest param) throws Exception {
        return new ResponseEntity<>(memberService.join(param), HttpStatus.CREATED);
    }

    public ResponseEntity<MemberLoginResponse> login(){
        return null;
    }

    public ResponseEntity<MemberLogoutResponse> logout(){
        return null;
    }

    public ResponseEntity<MemberUpdateResponse> update(){
        return null;
    }

    public ResponseEntity<MemberBanResponse> ban(){
        return null;
    }
}
