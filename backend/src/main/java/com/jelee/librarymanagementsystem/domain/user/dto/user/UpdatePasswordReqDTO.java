package com.jelee.librarymanagementsystem.domain.user.dto.user;

import lombok.Getter;

@Getter
public class UpdatePasswordReqDTO {
  private String password;
  private String repassword;
}
