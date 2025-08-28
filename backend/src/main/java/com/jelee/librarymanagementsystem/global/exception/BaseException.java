package com.jelee.librarymanagementsystem.global.exception;

import com.jelee.librarymanagementsystem.global.response.code.ErrorCode;

public class BaseException extends RuntimeException {
  
  private final ErrorCode errorCode;

  public BaseException(ErrorCode errorCode) {
    super(errorCode.getCode());
    this.errorCode = errorCode;
  }

  // 예외 메시지 추가 정보 포함
  public BaseException(ErrorCode errorCode, String message) {
    super(errorCode.getCode() + ": " + message);
    this.errorCode = errorCode;
  }

  public ErrorCode getErrorCode() {
    return errorCode;
  }

}
