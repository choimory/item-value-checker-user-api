package com.choimory.itemvaluechecker.api.userapi.common.exception;

import com.choimory.itemvaluechecker.api.userapi.common.dto.response.CommonResponseDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.naming.AuthenticationException;

@RestControllerAdvice("com.choimory")
@Slf4j
public class CommonExceptionHandler {
    @ExceptionHandler({Exception.class})
    public ResponseEntity<CommonResponseDto<?>> exception(Exception e) {
        return new ResponseEntity<>(CommonResponseDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<CommonResponseDto<?>> runtimeException(Exception e) {
        return new ResponseEntity<>(CommonResponseDto.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({CommonException.class})
    public ResponseEntity<CommonResponseDto<?>> commonException(CommonException e) {
        return new ResponseEntity<>(CommonResponseDto.builder()
                .status(e.getCode())
                .message(e.getMessage())
                .data(e.getMessage())
                .build(), e.getStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<CommonResponseDto<?>> authenticateException(Exception e) {
        return new ResponseEntity<>(CommonResponseDto.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .data(e.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }
}
