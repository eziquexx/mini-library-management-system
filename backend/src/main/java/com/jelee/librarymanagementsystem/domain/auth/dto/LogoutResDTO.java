package com.jelee.librarymanagementsystem.domain.auth.dto;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class LogoutResDTO {
  private Long id;
  private String username;
}
