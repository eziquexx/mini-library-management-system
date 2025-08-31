package com.jelee.librarymanagementsystem.domain.book.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.book.dto.client.UserBookDetailResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.client.UserBookListResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.client.UserBookSearchResDTO;
import com.jelee.librarymanagementsystem.domain.book.service.UserBookService;
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

  // 도서 전체 목록 조회 - 페이징
  @GetMapping()
  public ResponseEntity<?> allListBooks(
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {
    
    Page<UserBookListResDTO> listBooks = userBookService.allListBooks(page, size);

    String message = messageProvider.getMessage(BookSuccessCode.BOOK_LIST_FETCHED.getMessage());
    
    return ResponseEntity
              .status(BookSuccessCode.BOOK_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                BookSuccessCode.BOOK_LIST_FETCHED, 
                message, 
                listBooks));
  }

  // 도서 상세 조회
  @GetMapping("/{bookId}")
  public ResponseEntity<?> detailBook(@PathVariable("bookId") Long bookId) {
    
    // 서비스 로직
    UserBookDetailResDTO responseDTO = userBookService.detailBook(bookId);

    // 성공 메시지
    String message = messageProvider.getMessage(BookSuccessCode.BOOK_DETAIL.getMessage());
    
    return ResponseEntity
              .status(BookSuccessCode.BOOK_DETAIL.getHttpStatus())
              .body(ApiResponse.success(
                BookSuccessCode.BOOK_DETAIL, 
                message, 
                responseDTO));
  }


  // 도서 검색 - 페이징
  @GetMapping("/search")
  public ResponseEntity<?> searchBooks(
    @RequestParam String type,
    @RequestParam String keyword,
    @RequestParam(defaultValue = "0") int page,
    @RequestParam(defaultValue = "10") int size) {

    // 서비스 로직
    Page<UserBookSearchResDTO> responseDTO = userBookService.searchBooks(type, keyword, page, size);

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
