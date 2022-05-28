package com.choimory.itemvaluechecker.api.userapi.user.controller;

import com.choimory.itemvaluechecker.api.userapi.user.dto.request.UserRequestDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import com.choimory.itemvaluechecker.api.userapi.user.service.UserService;
import lombok.Getter;
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
    public ResponseEntity<UserResponseDto.View> view(@PathVariable final String id){
        return new ResponseEntity<>(userService.view(id), HttpStatus.OK);
    }

    @PutMapping("/join")
    public ResponseEntity<UserResponseDto.Join> join(@RequestBody(required = false) final UserRequestDto.Join param){
        return new ResponseEntity<>(userService.join(param), HttpStatus.CREATED);
    }
}
