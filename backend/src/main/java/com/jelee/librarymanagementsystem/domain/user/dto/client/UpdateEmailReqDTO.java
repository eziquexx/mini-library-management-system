package com.jelee.librarymanagementsystem.domain.user.dto.client;

import jakarta.validation.constraints.Email;
import lombok.Getter;

@Getter
public class UpdateEmailReqDTO {
  @Email
  private String email;
}
