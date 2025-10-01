package com.jelee.librarymanagementsystem.domain.book.service;

import org.springframework.data.domain.Page;
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

  /*
   * 공용: 도서 검색 (페이징)
   */
  public PageResponse<UserBookSearchResDTO> searchBooks(BookSearchType type, String keyword, int page, int size) {

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // Page<Book> 타입의 변수 생성
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

    // Book -> UserBookSearchResDTO로 형변환
    Page<UserBookSearchResDTO> pageDTO = result.map(UserBookSearchResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }
}
