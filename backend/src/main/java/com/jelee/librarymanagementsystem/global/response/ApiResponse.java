package com.jelee.librarymanagementsystem.global.response;

import com.jelee.librarymanagementsystem.global.enums.SuccessCode;

public class ApiResponse<T> {
  private String code;
  private String message;
  private T data;

  public ApiResponse(String code, String message, T data) {
    this.code = code;
    this.message = message;
    this.data = data;
  }

  // 성공
  public static <T> ApiResponse<T> success(SuccessCode successCode, String message, T data) {
    return new ApiResponse<>(successCode.getCode(), message, data);
  }

  // 실패, 에러
  public static <T> ApiResponse<T> error(String code, String message, T data) {
    return new ApiResponse<>(code, message, data);
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
