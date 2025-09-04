package com.jelee.librarymanagementsystem.domain.notice.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;

import lombok.Getter;

@Getter
public class AdminNoticeDetailResDTO {
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  private WriterDTO writer;

  public AdminNoticeDetailResDTO(Notice notice) {
    this.id = notice.getId();
    this.title = notice.getTitle();
    this.content = notice.getContent();
    this.createdDate = notice.getCreatedDate();
    this.updatedDate = notice.getUpdatedDate();
    this.writer = new WriterDTO(notice.getWriter());
  }
}
