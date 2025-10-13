package com.jelee.librarymanagementsystem.domain.notice.dto.admin;

import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;

import lombok.Getter;

@Getter
public class AdminNoticeDeleteResDTO {
  private Long id;

  public AdminNoticeDeleteResDTO (Notice notice) {
    this.id = notice.getId();
  }
}
