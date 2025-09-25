package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum NoticeSuccessCode implements SuccessCode {
  
  NOTICE_CREATED(HttpStatus.CREATED, "NOTICE_4100", "success.notice.created"),
  NOTICE_UPDATED(HttpStatus.OK, "NOTICE_4101", "success.notice.updated"),
  NOTICE_DELETED(HttpStatus.OK, "NOTICE_4102", "success.notice.deleted"),
  NOTICE_FETCHED(HttpStatus.OK, "NOTICE_4103", "success.notice.fetched"),
  NOTICE_LIST_FETCHED(HttpStatus.OK, "NOTICE_4104", "success.notice.list_fetched");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  NoticeSuccessCode(HttpStatus httpStatus, String code, String message) {
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
