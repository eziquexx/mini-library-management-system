package com.jelee.librarymanagementsystem.domain.admin.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.admin.dto.BookRequestDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookResponseDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookSearchReqDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookSearchResDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.admin.service.AdminBookService;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
import com.jelee.librarymanagementsystem.global.response.code.BookSuccessCode;
import com.jelee.librarymanagementsystem.global.util.MessageProvider;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/api/v1/admin/books")
@RequiredArgsConstructor
public class AdminBookController {

  private final AdminBookService adminBookService;
  private final MessageProvider messageProvider;
  
  // 도서 등록
  @PostMapping()
  public ResponseEntity<?> createBook(@RequestBody BookRequestDTO bookDTO) {
    BookResponseDTO responseDTO = adminBookService.createBook(bookDTO);

    String message = messageProvider.getMessage(BookSuccessCode.BOOK_CREATED.getMessage());

    return ResponseEntity
            .status(BookSuccessCode.BOOK_CREATED.getHttpStatus())
            .body(ApiResponse.success(
              BookSuccessCode.BOOK_CREATED, 
              message, 
              responseDTO));
  }
  // 도서 수정
  @PutMapping("/{bookId}")
  public ResponseEntity<?> updateBook(@PathVariable Long bookId, @RequestBody BookUpdateReqDTO bookDTO) {
    BookResponseDTO responseDTO = adminBookService.updateBook(bookId, bookDTO);
    
    String message = messageProvider.getMessage(BookSuccessCode.BOOK_UPDATED.getMessage());

    return ResponseEntity
            .status(BookSuccessCode.BOOK_UPDATED.getHttpStatus())
            .body(ApiResponse.success(
              BookSuccessCode.BOOK_UPDATED, 
              message, 
              responseDTO));
  }

  // 도서 삭제
  @DeleteMapping("/{bookId}")
  public ResponseEntity<?> deleteBook(@PathVariable Long bookId) {
    adminBookService.deleteBook(bookId);
    
    String message = messageProvider.getMessage(BookSuccessCode.BOOK_DELETED.getMessage());

    return ResponseEntity
              .status(BookSuccessCode.BOOK_DELETED.getHttpStatus())
              .body(ApiResponse.success(
                BookSuccessCode.BOOK_DELETED, 
                message, 
                bookId));
  }

  // 도서 검색
  @GetMapping("/search")
  public ResponseEntity<?> searchBook(@RequestBody BookSearchReqDTO request) {
    String keyword = request.getKeyword();
    List<BookSearchResDTO> books = adminBookService.searchBooksByKeyword(keyword);

    String message = messageProvider.getMessage(BookSuccessCode.BOOK_LIST_FETCHED.getMessage());

    return ResponseEntity
              .status(BookSuccessCode.BOOK_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                BookSuccessCode.BOOK_LIST_FETCHED, 
                message, 
                books));
  }
}
