package com.jelee.librarymanagementsystem.global.common;

public class ApiResponse<T> {
  private String code;
  private String message;
  private T data;

  public ApiResponse(String code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  public String getCode() {
    return code;
  }

  public String getMessage() {
    return message;
  }

  public T getData() {
    return data;
  }
}
