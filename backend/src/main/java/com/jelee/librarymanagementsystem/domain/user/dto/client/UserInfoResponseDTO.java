package com.jelee.librarymanagementsystem.domain.user.dto.client;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class UserInfoResponseDTO {
  private String username;
  private String email;
  private LocalDateTime joinDate;
  private LocalDateTime lastLoginDate;
}
