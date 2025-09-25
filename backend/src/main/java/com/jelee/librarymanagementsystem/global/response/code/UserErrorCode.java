package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum UserErrorCode implements ErrorCode {
  
  USER_NOT_FOUND(HttpStatus.NOT_FOUND, "USER_2000", "error.user.not_found"),
  USER_USERNAME_DUPLICATED(HttpStatus.CONFLICT, "USER_2001", "error.user.username_duplicate"),
  USER_EMAIL_DUPLICATED(HttpStatus.CONFLICT, "USER_2002", "error.user.email_duplicate"),
  INVALID_PASSWORD(HttpStatus.UNAUTHORIZED, "USER_2003", "error.user.invalid_password"),
  USER_EMAIL_SAME(HttpStatus.BAD_REQUEST, "USER_2004", "error.user.email_same"),
  USER_PASSWORD_SAME(HttpStatus.BAD_REQUEST, "USER_2005", "error.user.password_same"),
  USER_PASSWORD_MISMATCH(HttpStatus.BAD_REQUEST, "USER_2006", "error.user.password_mismatch"),
  USER_SEARCH_TYPE_INVALID(HttpStatus.BAD_REQUEST, "USER_2007", "error.user.search.type"),
  USER_STATUS_INACTIVE(HttpStatus.BAD_REQUEST, "USER_2008", "error.user.status_inactive"),
  USER_STATUS_SUSPENDED(HttpStatus.BAD_REQUEST, "USER_2009", "error.user.status_suspended"),
  USER_STATUS_DELETED(HttpStatus.BAD_REQUEST, "USER_2010", "error.user.status_deleted"),
  USER_STATUS_NOT_DELETED(HttpStatus.BAD_REQUEST, "USER_2011", "error.user.status_not_deleted");

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
