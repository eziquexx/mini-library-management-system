package com.jelee.librarymanagementsystem.domain.user.dto.client;

import lombok.Getter;

@Getter
public class UpdatePasswordReqDTO {
  private String password;
  private String repassword;
}
