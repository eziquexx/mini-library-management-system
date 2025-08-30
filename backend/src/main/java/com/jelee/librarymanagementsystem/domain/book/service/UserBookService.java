package com.jelee.librarymanagementsystem.domain.book.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.dto.client.UserBookListResDTO;
import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBookService {
  
  private final BookRepository bookRepository;

  // 도서 전체 목록 조회 - 페이징
  public Page<UserBookListResDTO> allListBooks(int page, int size) {

    // Pageable
    Pageable pageable = PageRequest.of(page, size);

    // book 조회 후 List로 변환
    Page<Book> result = bookRepository.findAll(pageable);
    List<UserBookListResDTO> dtoList = result.getContent()
        .stream()
        .map(UserBookListResDTO::new)
        .toList();

    // 응답은 Page 형태로
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 도서 상세 조회

  // 도서 검색 - 페이징
}
