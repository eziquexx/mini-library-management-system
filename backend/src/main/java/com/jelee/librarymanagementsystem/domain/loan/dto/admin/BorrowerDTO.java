package com.jelee.librarymanagementsystem.domain.loan.dto.admin;

import com.jelee.librarymanagementsystem.domain.user.entity.User;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowerDTO {
  private Long id;
  private String username;

  public BorrowerDTO(User user) {
    this.id = user.getId();
    this.username = user.getUsername();
  }
}
