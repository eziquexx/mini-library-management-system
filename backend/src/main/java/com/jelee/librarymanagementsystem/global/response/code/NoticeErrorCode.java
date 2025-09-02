package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public enum NoticeErrorCode implements ErrorCode {
  
  NOTICE_TITLE_BLANK(HttpStatus.BAD_REQUEST, "NOTICE_001", "error.notice.title.blank"),
  NOTICE_CONTENT_BLANK(HttpStatus.BAD_REQUEST, "NOTICE_002", "error.notice.content.blank"),
  NOTICE_CREATE_WRITER(HttpStatus.BAD_REQUEST, "NOTICE_003", "error.notice.create.writer"),
  NOTICE_UPDATE_FAILED(HttpStatus.BAD_REQUEST, "NOTICE_004", "error.notice.update_failed"),
  NOTICE_NOT_FOUND(HttpStatus.NOT_FOUND, "NOTICE_005", "error.notice.not_found");

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
