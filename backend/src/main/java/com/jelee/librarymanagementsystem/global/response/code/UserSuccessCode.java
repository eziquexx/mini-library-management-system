package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum UserSuccessCode implements SuccessCode {
  
  USER_CREATED(HttpStatus.CREATED, "USER_201", "success.user.created"),
  USER_EMAIL_UPDATE(HttpStatus.OK, "USER_200", "success.user.email_update"),
  USER_PASSWORD_UPDATE(HttpStatus.OK, "USER_200", "success.user.password_update"),
  USER_DELETE_ACCOUNT(HttpStatus.OK, "USER_202", "success.user.deleted"),
  USER_LIST_FETCHED(HttpStatus.OK, "USER_203", "success.user.list_fetched"),
  USER_SEARCH(HttpStatus.OK, "USER_204", "success.user.search");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  UserSuccessCode(HttpStatus httpStatus, String code, String message) {
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
