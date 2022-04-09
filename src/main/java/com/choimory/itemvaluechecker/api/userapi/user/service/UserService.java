package com.choimory.itemvaluechecker.api.userapi.user.service;

import com.choimory.itemvaluechecker.api.userapi.user.code.AuthLevel;
import com.choimory.itemvaluechecker.api.userapi.user.dto.request.UserRequestDto;
import com.choimory.itemvaluechecker.api.userapi.user.entity.User;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserAuthority;
import com.choimory.itemvaluechecker.api.userapi.user.entity.UserSuspension;
import com.choimory.itemvaluechecker.api.userapi.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public User view(String id){
        return userRepository.findById(id).orElse(null);
    }

    public User join(final UserRequestDto.Join param){
        return userRepository.save(param.toEntity());
    }
}
