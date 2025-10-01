package com.jelee.librarymanagementsystem.global.dto;

import java.util.List;

import org.springframework.data.domain.Page;

import lombok.Getter;

@Getter
public class PageResponse<T> {
  private final List<T> content;
  private final int page;
  private final int size;
  private final int totalPages;
  private final long totalElements;

  public PageResponse(Page<T> page) {
    this.content = page.getContent();
    this.page = page.getNumber();
    this.size = page.getSize();
    this.totalPages = page.getTotalPages();
    this.totalElements = page.getTotalElements();
  }
}
