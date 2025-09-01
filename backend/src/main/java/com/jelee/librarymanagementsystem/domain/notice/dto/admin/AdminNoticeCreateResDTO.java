package com.jelee.librarymanagementsystem.domain.notice.dto.admin;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class AdminNoticeCreateResDTO {
  private Long id;
  private String title;
  private LocalDateTime createdDate;
  private WriterDTO writer;
}
