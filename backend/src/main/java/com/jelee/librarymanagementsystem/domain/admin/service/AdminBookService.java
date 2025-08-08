package com.jelee.librarymanagementsystem.domain.admin.service;

import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.admin.dto.BookRequestDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookResponseDTO;
import com.jelee.librarymanagementsystem.domain.admin.entity.Book;
import com.jelee.librarymanagementsystem.domain.admin.repository.AdminBookRepository;
import com.jelee.librarymanagementsystem.global.exception.BaseException;
import com.jelee.librarymanagementsystem.global.response.code.BookErrorCode;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminBookService {

  private final AdminBookRepository adminBookRepository;
  
  // 도서 등록
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
      throw new BaseException(BookErrorCode.BOOK_LOCATION_DUPLICATED);
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
  public BookResponseDTO updateBook(BookRequestDTO bookDTO) {
    Book book = Book.builder()
                  .title(bookDTO.getTitle())
                  .isbn(bookDTO.getIsbn())
                  .author(bookDTO.getAuthor())
                  .publisher(bookDTO.getPublisher())
                  .publishedDate(bookDTO.getPublishedDate())
                  .location(bookDTO.getLocation())
                  .description(bookDTO.getDescription())
                  .build();
    
    Book updateBook = adminBookRepository.save(book);

    return new BookResponseDTO(updateBook);
  }
}
