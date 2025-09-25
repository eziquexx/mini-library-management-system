package com.jelee.librarymanagementsystem.domain.review.entity;

import java.time.LocalDateTime;

import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.ReviewErrorCode;

import jakarta.persistence.Entity;
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
@Table(name = "review")
public class Review {

  @Id
  @GeneratedValue(strategy = GenerationType.IDENTITY)
  private Long id;

  @ManyToOne(optional = false)
  @JoinColumn(name = "user_id")
  private User user;

  @ManyToOne(optional = false)
  @JoinColumn(name = "book_id")
  private Book book;

  private String content;
  private LocalDateTime createdDate;
  private LocalDateTime updatedDate;

  @PrePersist
  public void prePersist() {
    this.createdDate = LocalDateTime.now();
  }

  public void updateReview(String content) {
    if (content.isBlank() || content == null) {
      throw new BaseException(ReviewErrorCode.REVIEW_CONTENT_REQUIRED);
    }

    this.content = content;
    this.updatedDate = LocalDateTime.now();
  }
}
