package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public interface SuccessCode {
  HttpStatus getHttpStatus();
  String getCode();
  String getMessage();
}
