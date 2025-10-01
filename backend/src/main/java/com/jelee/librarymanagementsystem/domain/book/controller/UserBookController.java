package com.jelee.librarymanagementsystem.domain.book.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.book.dto.user.UserBookDetailResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.user.UserBookListResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.user.UserBookSearchResDTO;
import com.jelee.librarymanagementsystem.domain.book.enums.BookSearchType;
import com.jelee.librarymanagementsystem.domain.book.service.UserBookService;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.BookSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/books")
@RequiredArgsConstructor
public class UserBookController {
  
  private final UserBookService userBookService;
  private final MessageProvider messageProvider;

  /*
   * 공용: 도서 전체 목록 조회 (페이징)
   */
  @GetMapping()
  public ResponseEntity<?> allListBooks(
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size) {
    
      // 서비스로직
      PageResponse<UserBookListResDTO> responseDTO = userBookService.allListBooks(page, size);

      // 성공메시지
      String message = messageProvider.getMessage(BookSuccessCode.BOOK_LIST_FETCHED.getMessage());
      
      // 응답
      return ResponseEntity
                .status(BookSuccessCode.BOOK_LIST_FETCHED.getHttpStatus())
                .body(ApiResponse.success(
                  BookSuccessCode.BOOK_LIST_FETCHED, 
                  message, 
                  responseDTO));
  }

  /*
   * 공용: 도서 상세 조회
   */
  @GetMapping("/{bookId}")
  public ResponseEntity<?> detailBook(@PathVariable("bookId") Long bookId) {
    
    // 서비스 로직
    UserBookDetailResDTO responseDTO = userBookService.detailBook(bookId);

    // 성공 메시지
    String message = messageProvider.getMessage(BookSuccessCode.BOOK_FETCHED.getMessage());
    
    // 응답
    return ResponseEntity
              .status(BookSuccessCode.BOOK_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                BookSuccessCode.BOOK_FETCHED, 
                message, 
                responseDTO));
  }

  /*
   * 공용: 도서 검색 (페이징)
   */
  @GetMapping("/search")
  public ResponseEntity<?> searchBooks(
    @RequestParam("type") BookSearchType type,
    @RequestParam("keyword") String keyword,
    @RequestParam(value = "page", defaultValue = "0") int page,
    @RequestParam(value = "size", defaultValue = "10") int size) {

      // 서비스 로직
      PageResponse<UserBookSearchResDTO> responseDTO = userBookService.searchBooks(type, keyword, page, size);

      // 성공 메시지
      String message = messageProvider.getMessage(BookSuccessCode.BOOK_LIST_FETCHED.getMessage());

      // 반환
      return ResponseEntity
                .status(BookSuccessCode.BOOK_LIST_FETCHED.getHttpStatus())
                .body(ApiResponse.success(
                  BookSuccessCode.BOOK_LIST_FETCHED, 
                  message, 
                  responseDTO));
  }
}
