package com.jelee.librarymanagementsystem.domain.notice.dto.client;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.notice.dto.admin.WriterDTO;
import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;

import lombok.Getter;

@Getter
public class UserNoticeDetailResDTO {
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  private WriterDTO writer;

  public UserNoticeDetailResDTO(Notice notice) {
    this.id = notice.getId();
    this.title = notice.getTitle();
    this.content = notice.getContent();
    this.createdDate = notice.getCreatedDate();
    this.updatedDate = notice.getUpdatedDate();
    this.writer = new WriterDTO(notice.getWriter());
  }
}
