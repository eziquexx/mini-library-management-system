package com.jelee.librarymanagementsystem.domain.user.dto.client;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class UpdateEmailResDTO {
  private Long id;
  private String username;
  private String email;
  private LocalDateTime updatedAt;
}
