package com.jelee.librarymanagementsystem.domain.user.dto;

import lombok.Data;

@Data
public class UpdatePasswordDTO {
  private String password;
  private String repassword;
}
