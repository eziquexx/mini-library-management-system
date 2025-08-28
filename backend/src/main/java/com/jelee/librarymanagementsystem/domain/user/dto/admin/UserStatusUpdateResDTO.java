package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.global.enums.UserStatus;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserStatusUpdateResDTO {
  private Long id;
  private String username;
  private UserStatus status;
  private LocalDateTime updatedAt;
}
