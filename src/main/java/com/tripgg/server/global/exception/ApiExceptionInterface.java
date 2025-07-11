package com.tripgg.server.global.exception;

import com.tripgg.server.global.error.ErrorCodeInterface;

public interface ApiExceptionInterface {

  ErrorCodeInterface getErrorCodeInterface();

  String getErrorDescription();
}
