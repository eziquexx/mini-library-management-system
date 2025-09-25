package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum AuthErrorCode implements ErrorCode {
  
  AUTH_UNAUTHORIZED(HttpStatus.UNAUTHORIZED,"AUTH_1000", "error.auth.unauthorized"),
  AUTH_FORBIDDEN(HttpStatus.FORBIDDEN,"AUTH_1001", "error.auth.forbidden");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  AuthErrorCode(HttpStatus httpStatus, String code, String message) {
    this.httpStatus = httpStatus;
    this.code = code;
    this.message = message;
  }

  public HttpStatus getHttpStatus() {
    return httpStatus;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
