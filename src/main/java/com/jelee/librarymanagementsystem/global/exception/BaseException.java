package com.jelee.librarymanagementsystem.global.exception;

public class BaseException extends RuntimeException {
  
  private final ErrorCode errorCode;

  public BaseException(ErrorCode errorCode) {
    super(errorCode.getCode()); //errorCode.getCode()
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }
}
