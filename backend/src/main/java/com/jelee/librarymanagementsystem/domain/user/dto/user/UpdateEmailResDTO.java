package com.jelee.librarymanagementsystem.domain.user.dto.user;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.user.entity.User;

import lombok.Getter;

@Getter
public class UpdateEmailResDTO {
  private Long id;
  private String username;
  private String email;
  private LocalDateTime updatedAt;

  public UpdateEmailResDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.email = user.getEmail();
    this.updatedAt = user.getUpdatedAt();
  }
}
