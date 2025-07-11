package com.tripgg.server.global.exception;

import com.tripgg.server.global.error.ErrorCodeInterface;

import lombok.Getter;

@Getter
public class ApiException extends RuntimeException implements ApiExceptionInterface {

  private final ErrorCodeInterface errorCodeInterface;

  private final String errorDescription;

  public ApiException(ErrorCodeInterface errorCodeInterface) {
    super(errorCodeInterface.getMessage());
    this.errorCodeInterface = errorCodeInterface;
    this.errorDescription = errorCodeInterface.getMessage();
  }

  public ApiException(ErrorCodeInterface errorCodeInterface, String errorDescription) {
    super(errorDescription);
    this.errorCodeInterface = errorCodeInterface;
    this.errorDescription = errorCodeInterface.getMessage();
  }

  public ApiException(ErrorCodeInterface errorCodeInterface, Throwable tx) {
    super(tx);
    this.errorCodeInterface = errorCodeInterface;
    this.errorDescription = errorCodeInterface.getMessage();
  }

  public ApiException(ErrorCodeInterface errorCodeInterface, Throwable tx, String errorDescription) {
    super(tx);
    this.errorCodeInterface = errorCodeInterface;
    this.errorDescription = errorDescription;
  }
}
