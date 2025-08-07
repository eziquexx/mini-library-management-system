package com.jelee.librarymanagementsystem.global.response.code;

import org.springframework.http.HttpStatus;

public interface ErrorCode {
  HttpStatus getHttpStatus();
  String getCode();
  String getMessage();
}
