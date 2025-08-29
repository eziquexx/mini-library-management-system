package com.jelee.librarymanagementsystem.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class LoginReqDTO {
  private String username;
  private String password;
}
