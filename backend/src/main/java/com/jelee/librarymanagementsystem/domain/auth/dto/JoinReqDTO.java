package com.jelee.librarymanagementsystem.domain.auth.dto;

import lombok.Getter;

@Getter
public class JoinReqDTO {
  private String username;
  private String password;
  private String email;
}
