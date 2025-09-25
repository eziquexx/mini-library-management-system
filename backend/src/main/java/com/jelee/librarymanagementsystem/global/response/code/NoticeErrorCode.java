package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum NoticeErrorCode implements ErrorCode {
  
  NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE_4000", "error.notice.not_found"),
  NOTICE_TITLE_REQUIRED(HttpStatus.BAD_REQUEST, "NOTICE_4001", "error.notice.title.required"),
  NOTICE_CONTENT_REQUIRED(HttpStatus.BAD_REQUEST, "NOTICE_4002", "error.notice.content.required"),
  NOTICE_CREATE_AUTHORIZED(HttpStatus.BAD_REQUEST, "NOTICE_4003", "error.notice.create.unauthorized"),
  NOTICE_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "NOTICE_4004", "error.notice.update_failed");

  private final HttpStatus httpStatus;
  private final String code;
  private final String message;

  NoticeErrorCode(HttpStatus httpStatus, String code, String message) {
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
