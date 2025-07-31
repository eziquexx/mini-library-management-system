package com.jelee.librarymanagementsystem.global.response;

import com.jelee.librarymanagementsystem.global.enums.ErrorCode;
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
  public static <T> ApiResponse<T> error(ErrorCode errorCode, String message, T data) {
    return new ApiResponse<>(errorCode.getCode(), message, data);
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
