package com.choimory.itemvaluechecker.api.userapi.common.exception;

import com.choimory.itemvaluechecker.api.userapi.common.dto.response.CommonResponse;
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
    public ResponseEntity<CommonResponse<?>> exception(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({RuntimeException.class})
    public ResponseEntity<CommonResponse<?>> runtimeException(Exception e) {
        e.printStackTrace();
        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.INTERNAL_SERVER_ERROR.value())
                .message(HttpStatus.INTERNAL_SERVER_ERROR.getReasonPhrase())
                .data(e.getMessage())
                .build(), HttpStatus.INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({CommonException.class})
    public ResponseEntity<CommonResponse<?>> commonException(CommonException e) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(e.getCode())
                .message(e.getMessage())
                .data(e.getMessage())
                .build(), e.getStatus());
    }

    @ExceptionHandler({AuthenticationException.class})
    public ResponseEntity<CommonResponse<?>> authenticateException(Exception e) {
        return new ResponseEntity<>(CommonResponse.builder()
                .status(HttpStatus.UNAUTHORIZED.value())
                .message(HttpStatus.UNAUTHORIZED.getReasonPhrase())
                .data(e.getMessage())
                .build(), HttpStatus.UNAUTHORIZED);
    }
}
