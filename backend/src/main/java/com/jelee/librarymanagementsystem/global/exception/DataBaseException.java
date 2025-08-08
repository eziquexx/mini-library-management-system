package com.jelee.librarymanagementsystem.global.exception;

import com.jelee.librarymanagementsystem.global.response.code.ErrorCode;

public class DataBaseException extends BaseException {
  
  private final Object data;

  public DataBaseException(ErrorCode errorCode, Object data) {
    super(errorCode);
    this.data = data;
  }

  public Object getData() {
    return data;
  }
}
