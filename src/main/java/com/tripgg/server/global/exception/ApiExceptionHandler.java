package com.tripgg.server.global.exception;

import org.springframework.core.annotation.Order;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.tripgg.server.global.api.Api;
import com.tripgg.server.global.error.ErrorCodeInterface;

import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestControllerAdvice
@Order(value = Integer.MIN_VALUE)
public class ApiExceptionHandler {

  @ExceptionHandler(value = ApiException.class)
  public ResponseEntity<Object> apiException(
      ApiException e, HttpServletRequest request) {
    log.error("", e);

    Api<Object> errorResponse = Api.ERROR(
        e.getErrorCodeInterface(),
        request.getRequestURI());

    ErrorCodeInterface errorCode = e.getErrorCodeInterface();

    return ResponseEntity
        .status(errorCode.getHttpStatusCode())
        .body(
            errorResponse);

  }

  @ExceptionHandler(RuntimeException.class)
  public ResponseEntity<Api<Object>> handleRuntimeException(
      RuntimeException e,
      HttpServletRequest request) {

    Api<Object> errorResponse = Api.ERROR(
        500,
        "서버 내부 오류가 발생했습니다.",
        request.getRequestURI());

    return ResponseEntity.status(500).body(errorResponse);
  }
}