package com.jelee.librarymanagementsystem.domain.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookCreateReqDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookCreateResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookDetailResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookListResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookSearchResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.admin.AdminBookUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookSearchType;
import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;
import com.jelee.librarymanagementsystem.domain.user.entity.User;
import com.jelee.librarymanagementsystem.domain.user.repository.UserRepository;
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

  // 도서 수정
  @Transactional
  public AdminBookCreateResDTO updateBook(Long bookId, AdminBookUpdateReqDTO request) {

    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // 필수 필드 Null 체크(title, isbn, author, publisher, publishedDate, location)
    if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_TITLE_REQUIRED);
    }
    if (request.getIsbn() == null || request.getIsbn().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_ISBN_REQUIRED);
    }
    if (request.getAuthor() == null || request.getAuthor().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_AUTHOR_REQUIRED);
    }
    if (request.getPublisher() == null || request.getPublisher().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHER_REQUIRED);
    }
    if (request.getPublishedDate() == null) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHERDATE_REQUIRED);
    }
    if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_LOCATION_REQUIRED);
    }

    // location 중복 체크
    if (bookRepository.existsByLocationAndIdNot(request.getLocation(), bookId)) {
      Book sameLocationBook = bookRepository.findByLocation(request.getLocation());

      throw new DataBaseException(BookErrorCode.BOOK_LOCATION_DUPLICATED, sameLocationBook);
    }
    
    book.update(request);

    return new AdminBookCreateResDTO(book);
  }

  // 도서 삭제
  @Transactional
  public void deleteBook(Long bookId) {
    Optional<Book> optionalBook = bookRepository.findById(bookId);

    // Optional 안에 실제 Book이 있는지 확인
    if (optionalBook.isPresent()) {
      bookRepository.delete(optionalBook.get());
    } else {
      throw new BaseException(BookErrorCode.BOOK_NOT_FOUND);
    }
  }

  // 도서 검색
  // @Transactional
  // public List<BookSearchResDTO> searchBooksByKeyword(String keyword) {

  //   List<Book> books = bookRepository.findByTitleContainingIgnoreCaseOrAuthorContainingIgnoreCase(keyword, keyword);
  //   System.out.println("검색 키워드: [" + keyword + "]");

  //   if (books.isEmpty()) {
  //     throw new BaseException(BookErrorCode.BOOK_NOT_FOUND);
  //   }

  //   return books.stream()
  //       .map(book -> new BookSearchResDTO(
  //         book.getId(),
  //         book.getTitle(),
  //         book.getAuthor(),
  //         book.getPublisher(),
  //         book.getPublishedDate(),
  //         book.getLocation()
  //       ))
  //       .collect(Collectors.toList());
  // }

  // 도서 검색 - 페이징
  @Transactional
  public Page<AdminBookSearchResDTO> searchBooks(String typeStr, String keyword, int page, int size) {

    Pageable pageable = PageRequest.of(page, size);

    // 타입 예외처리
    BookSearchType type;
    try {
      type = BookSearchType.valueOf(typeStr.toUpperCase());
    } catch(IllegalArgumentException e) {
      throw new BaseException(BookErrorCode.BOOK_SEARCH_TYPE_FAILED);
    }
    
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

    List<AdminBookSearchResDTO> dtoList = result.getContent()
        .stream()
        .map(AdminBookSearchResDTO::new)
        .toList();

    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 도서 전체 목록 조회
  public Page<AdminBookListResDTO> allListBooks(int page, int size) {

    Pageable pageable = PageRequest.of(page, size);

    Page<Book> result = bookRepository.findAll(pageable);
    List<AdminBookListResDTO> dtoList = result.getContent()
        .stream()
        .map(AdminBookListResDTO::new)
        .toList();
    
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 도서 상세 조회
  public AdminBookDetailResDTO detailBook(Long bookId) {
    
    // bookId로 도서 조회
    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    return new AdminBookDetailResDTO(book);
  }
}
