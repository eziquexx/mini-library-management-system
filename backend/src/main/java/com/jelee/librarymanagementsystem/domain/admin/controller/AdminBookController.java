package com.jelee.librarymanagementsystem.domain.admin.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.admin.dto.BookRequestDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookResponseDTO;
import com.jelee.librarymanagementsystem.domain.admin.service.AdminBookService;
import com.jelee.librarymanagementsystem.global.enums.SuccessCode;
import com.jelee.librarymanagementsystem.global.response.ApiResponse;
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
  public ResponseEntity<?> createBook(@RequestBody BookRequestDTO bookDto) {
    BookResponseDTO responseDTO = adminBookService.createBook(bookDto);

    String message = messageProvider.getMessage(SuccessCode.BOOK_REGISTERED.getMessage());

    return ResponseEntity
            .status(SuccessCode.BOOK_REGISTERED.getHttpStatus())
            .body(ApiResponse.success(
              SuccessCode.BOOK_REGISTERED, 
              message, 
              responseDTO));
  }

}
