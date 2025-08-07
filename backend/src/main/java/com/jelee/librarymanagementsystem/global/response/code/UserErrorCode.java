package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
  
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER_401", "error.user.invalid_password"),
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_404", "error.user.not_found"),
  USER_USERNAME_DUPLICATED(HttpStatus.CONFLICT, "USER_001", "error.user.username_duplicate"),
  USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "USER_002", "error.user.email_duplicate"),
  USER_EMAIL_SAME(HttpStatus.BAD_REQUEST, "USER_003", "error.user.email_same"),
  USER_PASSWORD_SAME(HttpStatus.BAD_REQUEST, "USER_004", "error.user.password_same"),
  USER_PASSWORD_NOTSAME(HttpStatus.BAD_REQUEST, "USER_005", "error.password.notsame");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  UserErrorCode(HttpStatus httpStatus, String code, String message) {
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
