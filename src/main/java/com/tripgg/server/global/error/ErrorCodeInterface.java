package com.tripgg.server.global.error;

public interface ErrorCodeInterface {

  Integer getHttpStatusCode();

  Integer getCode();

  String getMessage();
}