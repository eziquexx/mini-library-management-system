package com.jelee.librarymanagementsystem.domain.notice.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;

import lombok.Getter;

@Getter
public class AdminNoticeCreateResDTO {
  private Long id;
  private String title;
  private LocalDateTime createdDate;
  private WriterDTO writer;

  public AdminNoticeCreateResDTO(Notice notice) {
    this.id = notice.getId();
    this.title = notice.getTitle();
    this.createdDate = notice.getCreatedDate();
    this.writer = new WriterDTO(notice.getWriter());
  }
}
