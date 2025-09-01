package com.jelee.librarymanagementsystem.domain.notice.dto.admin;

import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.enums.Role;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class WriterDTO {
  private Long id;
  private String username;
  private Role role;

  public WriterDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
    this.role = user.getRole();
  }
}
