package com.jelee.librarymanagementsystem.domain.book.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.dto.user.UserBookDetailResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.user.UserBookListResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.user.UserBookSearchResDTO;
import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookSearchType;
import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.BookErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class UserBookService {
  
  private final BookRepository bookRepository;

  /*
   * 공용: 도서 전체 목록 조회 (페이징)
   */
  public PageResponse<UserBookListResDTO> allListBooks(int page, int size) {

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // Book 조회 후 UserBookListResDTO로 변환
    Page<Book> result = bookRepository.findAll(pageable);
    Page<UserBookListResDTO> pageDTO = result.map(UserBookListResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }

  /*
   * 공용: 도서 상세 조회
   */
  public UserBookDetailResDTO detailBook(Long bookId) {

    // bookId 조회 및 예외 처리
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));
    
    // 반환
    return new UserBookDetailResDTO(book);
  }

  // 도서 검색 - 페이징
  @Transactional
  public Page<UserBookSearchResDTO> searchBooks(String typeStr, String keyword, int page, int size) {

    // Pageable
    Pageable pageable = PageRequest.of(page, size);

    // 타입 예외처리
    BookSearchType type;
    try {
      type = BookSearchType.valueOf(typeStr.toUpperCase());
    } catch(IllegalArgumentException e) {
      throw new BaseException(BookErrorCode.BOOK_SEARCH_TYPE_FAILED);
    }

    // 결과 담을 번수
    Page<Book> result;

    // 타입 switch문으로 case 실행
    switch(type) {
      case ALL:
        result = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword, pageable);
        break;
      case TITLE:
        result = bookRepository.findByTitleContainingIgnoreCase(keyword, pageable);
        break;
      case AUTHOR:
        result = bookRepository.findByAuthorContainingIgnoreCase(keyword, pageable);
        break;
      default:
        throw new IllegalArgumentException("Unexpected search type: " + type);
    }

    // result 비어있는지 체크
    if (result.isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_NOT_FOUND);
    }

    // List 타입 형태로 변형
    List<UserBookSearchResDTO> dtoList = result.getContent()
        .stream()
        .map(UserBookSearchResDTO::new)
        .toList();

    // Page 형태로 반환
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }
}
