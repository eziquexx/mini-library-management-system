package com.jelee.librarymanagementsystem.domain.book.service;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.book.dto.BookListResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.BookRequestDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.BookResponseDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.BookSearchResDTO;
import com.jelee.librarymanagementsystem.domain.book.dto.BookUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.book.entity.Book;
import com.jelee.librarymanagementsystem.domain.book.enums.BookSearchType;
import com.jelee.librarymanagementsystem.domain.book.repository.BookRepository;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.exception.DataBaseException;
import com.jelee.librarymanagementsystem.global.response.code.BookErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class BookService {

  private final BookRepository bookRepository;
  
  // 도서 등록
  @Transactional
  public BookResponseDTO createBook(BookRequestDTO request) {
    
    // 필수 필드 Null 체크(title, isbn, author, publisher, publishedDate, location)
    if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_TITLE_BLANK);
    }
    if (request.getIsbn() == null || request.getIsbn().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_ISBN_BLANK);
    }
    if (request.getAuthor() == null || request.getAuthor().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_AUTHOR_BLANK);
    }
    if (request.getPublisher() == null || request.getPublisher().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHER_BLANK);
    }
    if (request.getPublishedDate() == null) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHERDATE_BLANK);
    }
    if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_LOCATION_BLANK);
    }

    // location 중복 체크
    if (bookRepository.existsByLocation(request.getLocation())) {
      Book sameLocationBook = bookRepository.findByLocation(request.getLocation());

      throw new DataBaseException(BookErrorCode.BOOK_LOCATION_DUPLICATED, sameLocationBook);
    }

    Book book = Book.builder()
                  .title(request.getTitle())
                  .isbn(request.getIsbn())
                  .author(request.getAuthor())
                  .publisher(request.getPublisher())
                  .publishedDate(request.getPublishedDate())
                  .location(request.getLocation())
                  .description(request.getDescription())
                  .build();

    Book saveBook = bookRepository.save(book);

    return new BookResponseDTO(saveBook);
  }

  // 도서 수정
  @Transactional
  public BookResponseDTO updateBook(Long bookId, BookUpdateReqDTO request) {

    Book book = bookRepository.findById(bookId)
        .orElseThrow(() -> new BaseException(BookErrorCode.BOOK_NOT_FOUND));

    // 필수 필드 Null 체크(title, isbn, author, publisher, publishedDate, location)
    if (request.getTitle() == null || request.getTitle().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_TITLE_BLANK);
    }
    if (request.getIsbn() == null || request.getIsbn().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_ISBN_BLANK);
    }
    if (request.getAuthor() == null || request.getAuthor().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_AUTHOR_BLANK);
    }
    if (request.getPublisher() == null || request.getPublisher().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHER_BLANK);
    }
    if (request.getPublishedDate() == null) {
      throw new BaseException(BookErrorCode.BOOK_PUBLISHERDATE_BLANK);
    }
    if (request.getLocation() == null || request.getLocation().trim().isEmpty()) {
      throw new BaseException(BookErrorCode.BOOK_LOCATION_BLANK);
    }

    // location 중복 체크
    if (bookRepository.existsByLocationAndIdNot(request.getLocation(), bookId)) {
      Book sameLocationBook = bookRepository.findByLocation(request.getLocation());

      throw new DataBaseException(BookErrorCode.BOOK_LOCATION_DUPLICATED, sameLocationBook);
    }
    
    book.update(request);

    return new BookResponseDTO(book);
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
  public Page<BookSearchResDTO> searchBooks(String typeStr, String keyword, int page, int size) {

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

    List<BookSearchResDTO> dtoList = result.getContent()
        .stream()
        .map(BookSearchResDTO::new)
        .toList();

    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }

  // 도서 전체 목록 조회
  public Page<BookListResDTO> allListBooks(int page, int size) {

    Pageable pageable = PageRequest.of(page, size);

    Page<Book> result = bookRepository.findAll(pageable);
    List<BookListResDTO> dtoList = result.getContent()
        .stream()
        .map(BookListResDTO::new)
        .toList();
    
    return new PageImpl<>(dtoList, result.getPageable(), result.getTotalElements());
  }
}
