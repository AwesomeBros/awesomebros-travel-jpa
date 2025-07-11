package com.tripgg.server.global.api;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.tripgg.server.global.error.ErrorCode;
import com.tripgg.server.global.error.ErrorCodeInterface;

import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Api<T> {

  private int statusCode;
  private String message;
  private String timeStamp;
  private String path;

  @Valid
  private T body;

  public static <T> Api<T> OK(T data, String message) {
    Api<T> api = new Api<T>();
    api.statusCode = 200;
    api.message = message;
    api.body = data;
    api.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    api.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest().getRequestURI();
    return api;
  }

  public static <T> Api<T> OK(T data) {
    Api<T> api = new Api<T>();
    api.statusCode = 200;
    api.message = ErrorCode.OK.getMessage();
    api.body = data;
    api.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    api.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest().getRequestURI();
    return api;
  }

  public static Api<Object> ERROR(int statusCode, String message, String path) {
    Api<Object> api = new Api<>();
    api.statusCode = statusCode;
    api.message = message;
    api.body = null;
    api.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    api.path = path;
    return api;
  }

  public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface, String path) {
    Api<Object> api = new Api<>();
    api.statusCode = errorCodeInterface.getHttpStatusCode();
    api.message = errorCodeInterface.getMessage();
    api.body = null;
    api.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    api.path = path;
    return api;
  }

  public static Api<Object> ERROR(int statusCode, String message) {
    Api<Object> api = new Api<>();
    api.statusCode = statusCode;
    api.message = message;
    api.body = null;
    api.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    api.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest().getRequestURI();
    return api;
  }

  public static Api<Object> ERROR(ErrorCodeInterface errorCodeInterface) {
    Api<Object> api = new Api<>();
    api.statusCode = errorCodeInterface.getHttpStatusCode();
    api.message = errorCodeInterface.getMessage();
    api.body = null;
    api.timeStamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    api.path = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
        .getRequest().getRequestURI();
    return api;
  }
}