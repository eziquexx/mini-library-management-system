package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserDeleteResDTO {
  private Long id;
  private String username;
}
