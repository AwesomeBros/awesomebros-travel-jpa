package com.tripgg.server.global.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tripgg.server.global.api.Api;
import com.tripgg.server.global.error.ErrorCode;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MAX_VALUE)
public class GlobalExceptionHandler {

    @ExceptionHandler(value = Exception.class)
    public ResponseEntity<Api<Object>> exception(
            Exception exception, HttpServletRequest request) {
        log.error("", exception);

        return ResponseEntity
                .status(500)
                .body(
                        Api.ERROR(ErrorCode.SERVER_ERROR));
    }
}