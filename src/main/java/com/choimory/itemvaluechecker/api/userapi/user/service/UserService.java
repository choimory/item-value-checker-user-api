package com.choimory.itemvaluechecker.api.userapi.user.service;

import com.choimory.itemvaluechecker.api.userapi.common.exception.CommonException;
import com.choimory.itemvaluechecker.api.userapi.user.controller.UserController;
import com.choimory.itemvaluechecker.api.userapi.user.dto.request.UserJoinRequest;
import com.choimory.itemvaluechecker.api.userapi.user.dto.request.UserListRequest;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserJoinResponse;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserListResponse;
import com.choimory.itemvaluechecker.api.userapi.user.dto.response.UserViewResponse;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import com.choimory.itemvaluechecker.api.userapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public UserViewResponse view(final String id){
        return UserViewResponse.builder()
                .status(HttpStatus.OK.value())
                .message(HttpStatus.OK.getReasonPhrase())
                .user(UserViewResponse.UserViewResponseUser.toDto(userRepository.findById(id)
                        .orElseThrow(() -> new CommonException(HttpStatus.NOT_FOUND, HttpStatus.NOT_FOUND.value(), HttpStatus.NOT_FOUND.getReasonPhrase()))))
                .build();
    }

    public UserListResponse views(final UserListRequest param, Pageable pageable){
        return null;
    }

    public UserJoinResponse join(final UserJoinRequest param) throws Exception {
        /*필수값 검증*/
        param.requiredArgsValidate();

        /*요청값 검증*/
        param.isIdValidate();
        param.isPasswordValidate();
        param.isEmailValidate();

        /*중복여부 확인*/
        if(userRepository.existsById(param.getId())){
            throw new CommonException(HttpStatus.BAD_REQUEST,
                    UserJoinRequest.UserJoinRequestValidate.ID_DUPLICATE.getCode(),
                    UserJoinRequest.UserJoinRequestValidate.ID_DUPLICATE.getMessage());
        }

        /*저장*/
        User user = userRepository.save(param.toEntity());

        /*반환*/
        UserJoinResponse response = UserJoinResponse.builder()
                .status(HttpStatus.CREATED.value())
                .message(HttpStatus.CREATED.getReasonPhrase())
                .build();
        response.add(WebMvcLinkBuilder.linkTo(UserController.class).slash(user.getId()).withRel("view-id"));

        return response;
    }
}
