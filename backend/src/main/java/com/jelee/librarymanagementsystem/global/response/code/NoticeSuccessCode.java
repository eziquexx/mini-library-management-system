package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum NoticeSuccessCode implements SuccessCode {
  
  NOTICE_CREATED(HttpStatus.CREATED, "NOTICE_201", "success.notice.created"),
  NOTICE_UPDATED(HttpStatus.OK, "NOTICE_202", "success.notice.updated"),
  NOTICE_DELETED(HttpStatus.OK, "NOTICE_203", "success.notice.deleted"),
  NOTICE_LIST_FETCHED(HttpStatus.OK, "NOTICE_204", "success.notice.list_fetched");

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
