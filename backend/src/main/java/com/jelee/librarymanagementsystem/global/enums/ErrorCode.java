package com.jelee.librarymanagementsystem.global.enums;

import org.springframework.http.HttpStatus;

public enum ErrorCode {

  USER_USERNAME_DUPLICATED(HttpStatus.CONFLICT, "USER_001", "error.username.duplicate"),
  USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "USER_002", "error.email.duplicate"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "error.user.not_found"),
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER_401", "error.user.invalid_password"),
  UNAUTHORIZED(HttpStatus.UNAUTHORIZED, "AUTH_401", "error.unauthorized"),
  FORBIDDEN(HttpStatus.FORBIDDEN, "AUTH403", "error.forbidden");

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
