package com.choimory.itemvaluechecker.api.userapi.user.service;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.user.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.user.dto.request.UserRequestDto;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserResponseDto;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserAuthority;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserSuspension;
import com.choimory.itemvaluechecker.api.userapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserResponseDto.View view(final String id){
        return UserResponseDto.View.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .user(userRepository.findById(id)
                        .orElseThrow(() -> new CommonException(HttpStatus.NO_CONTENT, HttpStatus.NO_CONTENT.getReasonPhrase())))
                .build();
    }

    public UserResponseDto.Join join(final UserRequestDto.Join param){
        User user = userRepository.save(param.toEntity());
        return UserResponseDto.Join.builder()
                .status(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .token("Bearer ")
                .build();
    }
}
