package com.choimory.itemvaluechecker.api.userapi.user.controller;

import com.choimory.itemvaluechecker.api.userapi.user.dto.request.UserRequestDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserBanResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserJoinResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserLoginResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserLogoutResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserUpdateResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserViewResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {
    private final UserService userService;

    @GetMapping("/{id}")
    public ResponseEntity<UserViewResponseDto> view(@PathVariable final String id){
        return new ResponseEntity<>(userService.view(id), HttpStatus.OK);
    }

    @PutMapping("/join")
    public ResponseEntity<UserJoinResponseDto> join(@RequestBody(required = false) final UserRequestDto.Join param){
        return new ResponseEntity<>(userService.join(param), HttpStatus.CREATED);
    }

    public ResponseEntity<UserLoginResponseDto> login(){
        return null;
    }

    public ResponseEntity<UserLogoutResponseDto> logout(){
        return null;
    }

    public ResponseEntity<UserUpdateResponseDto> update(){
        return null;
    }

    public ResponseEntity<UserBanResponseDto> ban(){
        return null;
    }
}
