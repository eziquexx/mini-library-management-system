package com.jelee.librarymanagementsystem.global.exception;

public class ErrorResponse {
  
  private final String code;
  private final String message;

  public ErrorResponse(ErrorCode errorCode) {
    this.code = errorCode.getCode();
    this.message = MessageProvider.getMessage(errorCode.getCode());
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }
}
