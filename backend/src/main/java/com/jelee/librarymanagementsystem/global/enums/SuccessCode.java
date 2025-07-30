package com.jelee.librarymanagementsystem.global.enums;

import org.springframework.http.HttpStatus;

public enum SuccessCode {

  USER_CREATED(HttpStatus.CREATED, "USER_201", "success.user.signup"),
  USER_LOGIN_SUCCESS(HttpStatus.OK, "AUTH_200", "success.user.login"),
  USER_LOGOUT_SUCCESS(HttpStatus.OK, "AUTH_200", "success.user.logout"),
  BOOK_REGISTERED(HttpStatus.CREATED, "BOOK_201", "success.book.registered");
 
  // 필드
  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  SuccessCode(HttpStatus httpStatus, String code, String message) {
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
