package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum AuthSuccessCode implements SuccessCode {

  AUTH_LOGIN_SUCCESS(HttpStatus.OK, "AUTH_200", "success.auth.login"),
  AUTH_LOGOUT_SUCCESS(HttpStatus.OK, "AUTH_201", "success.auth.logout"),
  AUTH_USER_VERIFIED(HttpStatus.OK, "AUTH_202", "success.auth.verified");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  AuthSuccessCode(HttpStatus httpStatus, String code, String message) {
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
