package com.jelee.librarymanagementsystem.domain.auth.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class JoinRequest {
  private String username;
  private String password;
  private String email;
}
