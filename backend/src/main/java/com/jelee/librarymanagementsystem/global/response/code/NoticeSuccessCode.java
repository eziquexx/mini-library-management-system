package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum NoticeSuccessCode implements SuccessCode {
  
  NOTICE_CREATED(HttpStatus.CREATED, "NOTICE_201", "success.notice.created");

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
