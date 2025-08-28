package com.jelee.librarymanagementsystem.domain.user.dto;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.global.enums.Role;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

public class UserAdminDTO {

  // 권한 수정 요청 dto
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class RoleUpdateReqDTO {
    private Long userId;
    private Role role;
  }

  // 권한 수정 응답 dto
  @Data
  @NoArgsConstructor
  @AllArgsConstructor
  @Builder
  public static class RoleUpdateResDTO {
    private Long id;
    private String username;
    private Role role;
    private LocalDateTime updatedAt;
  }
}
