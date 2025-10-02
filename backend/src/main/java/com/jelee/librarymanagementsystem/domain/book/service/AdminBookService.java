package com.jelee.librarymanagementsystem.domain.book.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookCreateResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookDeleteResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookDetailResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookListResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookSearchResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookUpdateResDTO;
import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookSearchType;
import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
import com.jelee.librarymanagementsystem.global.dto.PageResponse;
import com.jelee.librarymanagementsystem.global.enums.Role;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.exception.DataBaseException;
import com.jelee.librarymanagementsystem.global.response.code.AuthErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.BookErrorCode;
import com.jelee.librarymanagementsystem.global.response.code.UserErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminBookService {

  private final BookRepository bookRepository;
  private final UserRepository userRepository;
  
  /*
   * 관리자: 도서 등록
   */
  @Transactional
  public AdminBookCreateResDTO createBook(AdminBookCreateReqDTO requestDTO, Long userId) {
    
    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 필수 필드 Null 체크
    // title, isbn, author, publisher, publishedDate, location
    if (requestDTO.getTitle() == null || requestDTO.getTitle().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_TITLE_REQUIRED);
    }
    if (requestDTO.getIsbn() == null || requestDTO.getIsbn().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_ISBN_REQUIRED);
    }
    if (requestDTO.getAuthor() == null || requestDTO.getAuthor().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_AUTHOR_REQUIRED);
    }
    if (requestDTO.getPublisher() == null || requestDTO.getPublisher().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHER_REQUIRED);
    }
    if (requestDTO.getPublishedDate() == null) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHERDATE_REQUIRED);
    }
    if (requestDTO.getLocation() == null || requestDTO.getLocation().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_LOCATION_REQUIRED);
    }

    // location 중복 체크
    if (bookRepository.existsByLocation(requestDTO.getLocation())) {
      Book sameLocationBook = bookRepository.findByLocation(requestDTO.getLocation());

      throw new DataBaseException(BookErrorCode.BOOK_LOCATION_DUPLICATED, sameLocationBook.getId());
    }

    // Book 엔티티 생성
    Book book = Book.builder()
                  .title(requestDTO.getTitle())
                  .isbn(requestDTO.getIsbn())
                  .author(requestDTO.getAuthor())
                  .publisher(requestDTO.getPublisher())
                  .publishedDate(requestDTO.getPublishedDate())
                  .location(requestDTO.getLocation())
                  .description(requestDTO.getDescription())
                  .build();

    // Book 저장
    Book saveBook = bookRepository.save(book);

    // 반환
    return new AdminBookCreateResDTO(saveBook);
  }

  /*
   * 관리자: 도서 전체 목록 조회
   */
  public PageResponse<AdminBookListResDTO> allListBooks(int page, int size, Long userId) {

    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // Book 형태로 페이지 조회 후 AdminBOokListResDTO로 변환
    Page<Book> result = bookRepository.findAll(pageable);
    Page<AdminBookListResDTO> pageDTO = result.map(AdminBookListResDTO::new);
    
    // 반환
    return new PageResponse<>(pageDTO);
  }

  /*
   * 관리자: 도서 상세 조회
   */
  public AdminBookDetailResDTO detailBook(Long bookId, Long userId) {
    
    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // bookId로 도서 조회
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // 반환
    return new AdminBookDetailResDTO(book);
  }

  /*
   * 관리자: 도서 수정
   */
  @Transactional
  public AdminBookUpdateResDTO updateBook(Long bookId, AdminBookUpdateReqDTO requestDTO, Long userId) {

    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 도서 조회 및 예외 처리
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // 필수 필드 Null 체크
    // (title, isbn, author, publisher, publishedDate, location)
    if (requestDTO.getTitle() == null || requestDTO.getTitle().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_TITLE_REQUIRED);
    }
    if (requestDTO.getIsbn() == null || requestDTO.getIsbn().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_ISBN_REQUIRED);
    }
    if (requestDTO.getAuthor() == null || requestDTO.getAuthor().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_AUTHOR_REQUIRED);
    }
    if (requestDTO.getPublisher() == null || requestDTO.getPublisher().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHER_REQUIRED);
    }
    if (requestDTO.getPublishedDate() == null) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHERDATE_REQUIRED);
    }
    if (requestDTO.getLocation() == null || requestDTO.getLocation().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_LOCATION_REQUIRED);
    }

    // location 중복 체크
    if (bookRepository.existsByLocationAndIdNot(requestDTO.getLocation(), bookId)) {
      Book sameLocationBook = bookRepository.findByLocation(requestDTO.getLocation());

      throw new DataBaseException(BookErrorCode.BOOK_LOCATION_DUPLICATED, sameLocationBook);
    }
    
    // 도서 DB에 업데이트
    book.update(requestDTO);

    // 반환
    return new AdminBookUpdateResDTO(book);
  }

  /*
   * 관리자: 도서 삭제
   */
  @Transactional
  public AdminBookDeleteResDTO deleteBook(Long bookId, Long userId) {
    
    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 도서 조회 및 예외 처리
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // 응답용 도서 저장 후 도서 삭제
    Book responseBook = book;
    bookRepository.delete(book);

    // 반환
    return new AdminBookDeleteResDTO(responseBook);
  }

  /*
   * 관리자: 도서 검색 (페이징)
   */
  public PageResponse<AdminBookSearchResDTO> searchBooks(BookSearchType type, String keyword, int page, int size, Long userId) {

    // 관리자 권환 조회 및 예외 처리
    User user = userRepository.findById(userId)
        .orElseThrow(() -> new BaseException(UserErrorCode.USER_NOT_FOUND));
    
    if (user.getRole() != Role.ROLE_ADMIN) {
      throw new BaseException(AuthErrorCode.AUTH_FORBIDDEN);
    }

    // 페이징 정의
    Pageable pageable = PageRequest.of(page, size);

    // 타이별 도서 검색
    Page<Book> result;
    switch (type) {
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
        throw new IllegalStateException("Unexpected search type: " + type);
    }

    if (result.isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_NOT_FOUND);
    }

    // Page<Book> -> AdminBookSearchResDTO 맵핑
    Page<AdminBookSearchResDTO> pageDTO = result.map(AdminBookSearchResDTO::new);

    // 반환
    return new PageResponse<>(pageDTO);
  }

}
