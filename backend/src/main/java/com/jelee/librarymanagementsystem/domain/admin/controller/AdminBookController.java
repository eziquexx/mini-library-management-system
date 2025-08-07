package com.jelee.librarymanagementsystem.domain.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.admin.dto.BookRequestDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookResponseDTO;
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
  @PutMapping()
  public ResponseEntity<?> updateBook(@RequestBody BookRequestDTO bookDTO) {
    BookResponseDTO responseDTO = adminBookService.updateBook(bookDTO);
    
    String message = messageProvider.getMessage(BookSuccessCode.BOOK_UPDATED.getMessage());

    return ResponseEntity
            .status(BookSuccessCode.BOOK_UPDATED.getHttpStatus())
            .body(ApiResponse.success(
              BookSuccessCode.BOOK_UPDATED, 
              message, 
              responseDTO));
  }
}
