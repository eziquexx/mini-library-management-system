package com.jelee.librarymanagementsystem.domain.notice.entity;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.notice.dto.admin.AdminNoticeUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.user.entity.User;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "notice")
public class Notice {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;
  private String title;
  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;
  
  @ManyToOne(fetch = FetchType.LAZY)
  @JoinColumn(name = "writer_id", nullable = false)
  private User writer;

  // 글 작성시 자동으로 현재 시간으로 등록
  @PrePersist
  private void prePersist() {
    this.createdDate = LocalDateTime.now();
  }

  // 수정 사용자 함수
  public void update(AdminNoticeUpdateReqDTO requestDTO, User writer) {
    this.title = requestDTO.getTitle();
    this.content = requestDTO.getContent();
    this.updatedDate = LocalDateTime.now();
    this.writer = writer;
  }
}
