package com.jelee.librarymanagementsystem.global.exception;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

  USER_USERNAME_DUPLICATED(HttpStatus.CONFLICT, "USER_001", "error.username.duplicate"),
  USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "USER_002", "error.email.duplicate");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  ErrorCode(HttpStatus httpStatus, String code, String message) {
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
