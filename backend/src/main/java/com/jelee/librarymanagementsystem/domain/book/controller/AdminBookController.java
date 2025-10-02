package com.jelee.librarymanagementsystem.domain.book.controller;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookCreateResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookDetailResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookListResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookSearchResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookUpdateResDTO;
import com.jelee.librarymanagementsystem.domain.book.service.AdminBookService;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
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
  
  /*
   * 관리자: 도서 등록
   */
  @PostMapping()
  public ResponseEntity<?> createBook(
    @RequestBody AdminBookCreateReqDTO requestDTO, 
    @AuthenticationPrincipal User user) {

      // 서비스로직
      AdminBookCreateResDTO responseDTO = adminBookService.createBook(requestDTO, user.getId());

      // 성공메시지
      String message = messageProvider.getMessage(BookSuccessCode.BOOK_CREATED.getMessage());

      // 응답
      return ResponseEntity
              .status(BookSuccessCode.BOOK_CREATED.getHttpStatus())
              .body(ApiResponse.success(
                BookSuccessCode.BOOK_CREATED, 
                message, 
                responseDTO));
  }

  /*
   * 관리자: 도서 전체 목록 조회
   */
  @GetMapping()
  public ResponseEntity<?> allListBooks(
    @RequestParam(value = "page", defaultValue = "0") int page, 
    @RequestParam(value = "size", defaultValue = "10") int size,
    @AuthenticationPrincipal User user) {

      // 서비스로직
      PageResponse<AdminBookListResDTO> responseDTO = adminBookService.allListBooks(page, size, user.getId());

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
   * 관리자: 도서 상세 조회
   */
  @GetMapping("/{bookId}")
  public ResponseEntity<?> detailBook(
    @PathVariable("bookId") Long bookId, 
    @AuthenticationPrincipal User user) {

      // 서비스로직
      AdminBookDetailResDTO responseDTO = adminBookService.detailBook(bookId, user.getId());

      // 성공메시지
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
   * 관리자: 도서 수정
   */
  // 도서 수정
  @PatchMapping("/{bookId}")
  public ResponseEntity<?> updateBook(
    @PathVariable("bookId") Long bookId, 
    @RequestBody AdminBookUpdateReqDTO requestDTO,
    @AuthenticationPrincipal User user) {

      // 서비스로직
      AdminBookUpdateResDTO responseDTO = adminBookService.updateBook(bookId, requestDTO, user.getId());
      
      // 성공메시지
      String message = messageProvider.getMessage(BookSuccessCode.BOOK_UPDATED.getMessage());

      // 응답
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
  // @GetMapping("/search")
  // public ResponseEntity<?> searchBook(@RequestBody BookSearchReqDTO request) {
  //   String keyword = request.getKeyword();
  //   List<BookSearchResDTO> books = adminBookService.searchBooksByKeyword(keyword);

  //   String message = messageProvider.getMessage(BookSuccessCode.BOOK_LIST_FETCHED.getMessage());

  //   return ResponseEntity
  //             .status(BookSuccessCode.BOOK_LIST_FETCHED.getHttpStatus())
  //             .body(ApiResponse.success(
  //               BookSuccessCode.BOOK_LIST_FETCHED, 
  //               message, 
  //               books));
  // }

  // 도서 검색 - 페이징
  // Page 기능 사용
  @GetMapping("/search")
  public ResponseEntity<?> searchBooks(
      @RequestParam(name = "type") String type,
      @RequestParam(name = "keyword") String keyword,
      @RequestParam(name = "page", defaultValue = "0") int page,
      @RequestParam(name = "size", defaultValue = "10") int size) {

    Page<AdminBookSearchResDTO> books = adminBookService.searchBooks(type, keyword, page, size);

    String message = messageProvider.getMessage(BookSuccessCode.BOOK_LIST_FETCHED.getMessage());

    return ResponseEntity
              .status(BookSuccessCode.BOOK_LIST_FETCHED.getHttpStatus())
              .body(ApiResponse.success(
                BookSuccessCode.BOOK_LIST_FETCHED, 
                message, 
                books));
  }

}
