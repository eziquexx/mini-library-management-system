package com.jelee.librarymanagementsystem.domain.user.dto;

import jakarta.validation.constraints.Email;
import lombok.Data;

@Data
public class UpdateEmailDTO {
  @Email
  private String email;
}
