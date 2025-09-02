package com.jelee.librarymanagementsystem.domain.notice.dto.admin;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.notice.entity.Notice;

import lombok.Getter;

@Getter
public class AdminNoticeUpdateResDTO {
  private Long id;
  private WriterDTO writer;
  private LocalDateTime updatedDate;

  public AdminNoticeUpdateResDTO(Notice notice) {
    this.id = notice.getId();
    this.writer = new WriterDTO(notice.getWriter());
    this.updatedDate = notice.getUpdatedDate();
  }
}
