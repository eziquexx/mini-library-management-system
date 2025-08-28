package com.jelee.librarymanagementsystem.domain.user.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.global.enums.Role;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UserRoleUpdatedResDTO {
  private Long id;
  private String username;
  private Role role;
  private LocalDateTime updatedAt;
}
