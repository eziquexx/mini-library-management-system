package com.jelee.librarymanagementsystem.domain.book.entity;

import java.time.LocalDate;
import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.book.enums.BookStatus;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
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
@Table(name = "book")
public class Book {
  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  private String title;
  private String isbn;
  private String author;
  private String publisher;
  private LocalDate publishedDate;
  
  @Enumerated(EnumType.STRING)
  private BookStatus status;
  private String location;

  @Column(columnDefinition = "TEXT")
  private String description;

  private LocalDateTime createdAt;
  private LocalDateTime updatedAt;

  @PrePersist
  public void prePersist() {
    this.createdAt = LocalDateTime.now();
    this.status = BookStatus.AVAILABLE;
  }

  @PreUpdate
  public void preUpdate() {
    this.updatedAt = LocalDateTime.now();
  }

  // 업데이트 메서드
  public void update(AdminBookUpdateReqDTO request) {
    this.title = request.getTitle();
    this.isbn = request.getIsbn();
    this.author = request.getAuthor();
    this.publisher = request.getPublisher();
    this.publishedDate = request.getPublishedDate();
    this.location = request.getLocation();
    this.description = request.getDescription();
  }
}
