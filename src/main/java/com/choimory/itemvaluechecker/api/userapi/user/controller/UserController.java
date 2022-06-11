package com.choimory.itemvaluechecker.api.userapi.user.controller;

import com.choimory.itemvaluechecker.api.userapi.common.dto.request.CommonPageRequest;
import com.choimory.itemvaluechecker.api.userapi.user.dto.request.UserJoinRequest;
import com.choimory.itemvaluechecker.api.userapi.user.dto.request.UserListRequest;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserBanResponse;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserJoinResponse;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserListResponse;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserLoginResponse;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserLogoutResponse;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserUpdateResponse;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserViewResponse;
import com.choimory.itemvaluechecker.api.userapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserViewResponse> view(@PathVariable final String id){
        return new ResponseEntity<>(userService.view(id), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<UserListResponse> views(final UserListRequest param,
                                                  final CommonPageRequest commonPageRequest){
        return new ResponseEntity<>(userService.views(param, Pageable.ofSize(10)), HttpStatus.OK);
    }

    @PutMapping("/join")
    public ResponseEntity<UserJoinResponse> join(@RequestBody(required = false) final UserJoinRequest param) throws Exception {
        return new ResponseEntity<>(userService.join(param), HttpStatus.CREATED);
    }

    public ResponseEntity<UserLoginResponse> login(){
        return null;
    }

    public ResponseEntity<UserLogoutResponse> logout(){
        return null;
    }

    public ResponseEntity<UserUpdateResponse> update(){
        return null;
    }

    public ResponseEntity<UserBanResponse> ban(){
        return null;
    }
}
