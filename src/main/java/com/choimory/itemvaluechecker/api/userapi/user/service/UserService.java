package com.choimory.itemvaluechecker.api.userapi.user.service;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.user.dto.request.UserJoinRequestDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserJoinResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserViewResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import com.choimory.itemvaluechecker.api.userapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserViewResponseDto view(final String id){
        return UserViewResponseDto.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .user(UserViewResponseDto.User.toDto(userRepository.findById(id)
                        .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.getReasonPhrase()))))
                .build();
    }

    public UserJoinResponseDto join(final UserJoinRequestDto param){
        /*요청값 검증*/
        param.isIdValidate();
        param.isPasswordValidate();
        param.isEmailValidate();

        /*저장*/
        User user = userRepository.save(param.toEntity());
        /*응답값 검증*/
        /*반환*/
        return UserJoinResponseDto.builder()
                .status(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .token("Bearer ")
                .build();
    }
}
