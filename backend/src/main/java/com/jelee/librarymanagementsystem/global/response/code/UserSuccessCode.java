package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum UserSuccessCode implements SuccessCode {
  
  USER_CREATED(HttpStatus.CREATED, "USER_2100", "success.user.created"),
  USER_EMAIL_UPDATE(HttpStatus.OK, "USER_2101", "success.user.email_updated"),
  USER_PASSWORD_UPDATE(HttpStatus.OK, "USER_2102", "success.user.password_updated"),
  USER_ACCOUNT_DELETED(HttpStatus.OK, "USER_2103", "success.user.deleted"),
  USER_FETCHED(HttpStatus.OK, "USER_2104", "success.user.fetched"),
  USER_LIST_FETCHED(HttpStatus.OK, "USER_2105", "success.user.list_fetched"),
  USER_ROLE_UPDATED(HttpStatus.OK, "USER_2106", "success.user.role_updated"),
  USER_STATUS_UPDATED(HttpStatus.OK, "USER_2107", "success.user.status_updated");

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
