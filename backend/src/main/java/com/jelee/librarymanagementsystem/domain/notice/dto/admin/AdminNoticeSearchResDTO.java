package com.jelee.librarymanagementsystem.domain.notice.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;

import lombok.Getter;

@Getter
public class AdminNoticeSearchResDTO {
  private Long id;
  private String title;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  private WriterDTO writer;

  public AdminNoticeSearchResDTO(Notice notice) {
    this.id = notice.getId();
    this.title = notice.getTitle();
    this.createdDate = notice.getCreatedDate();
    this.updatedDate = notice.getUpdatedDate();
    this.writer = new WriterDTO(notice.getWriter());
  }
}
