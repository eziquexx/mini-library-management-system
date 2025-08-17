package com.jelee.librarymanagementsystem.domain.admin.service;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.admin.dto.BookRequestDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookResponseDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookUpdateReqDTO;
import com.jelee.librarymanagementsystem.domain.admin.entity.Book;
import com.jelee.librarymanagementsystem.domain.admin.repository.AdminBookRepository;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.exception.DataBaseException;
import com.jelee.librarymanagementsystem.global.response.code.BookErrorCode;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminBookService {

  private final AdminBookRepository adminBookRepository;
  
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
    if (adminBookRepository.existsByLocation(request.getLocation())) {
      Book sameLocationBook = adminBookRepository.findByLocation(request.getLocation());

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

    Book saveBook = adminBookRepository.save(book);

    return new BookResponseDTO(saveBook);
  }

  // 도서 수정
  @Transactional
  public BookResponseDTO updateBook(Long bookId, BookUpdateReqDTO request) {

    Book book = adminBookRepository.findById(bookId)
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
    if (adminBookRepository.existsByLocationAndIdNot(request.getLocation(), bookId)) {
      Book sameLocationBook = adminBookRepository.findByLocation(request.getLocation());

      throw new DataBaseException(BookErrorCode.BOOK_LOCATION_DUPLICATED, sameLocationBook);
    }
    
    book.update(request);

    return new BookResponseDTO(book);
  }

  // 도서 삭제
  @Transactional
  public void deleteBook(Long bookId) {
    Optional<Book> optionalBook = adminBookRepository.findById(bookId);

    // Optional 안에 실제 Book이 있는지 확인
    if (optionalBook.isPresent()) {
      adminBookRepository.delete(optionalBook.get());
    } else {
      throw new BaseException(BookErrorCode.BOOK_NOT_FOUND);
    }
  }
}
