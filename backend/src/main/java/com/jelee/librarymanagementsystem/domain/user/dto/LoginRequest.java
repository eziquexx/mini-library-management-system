package com.jelee.librarymanagementsystem.domain.user.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginRequest {
  private String username;
  private String password;
}
