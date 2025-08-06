package com.jelee.librarymanagementsystem.domain.admin.service;

import org.springframework.stereotype.Service;

import com.jelee.librarymanagementsystem.domain.admin.dto.BookRequestDTO;
import com.jelee.librarymanagementsystem.domain.admin.dto.BookResponseDTO;
import com.jelee.librarymanagementsystem.domain.admin.entity.Book;
import com.jelee.librarymanagementsystem.domain.admin.repository.AdminBookRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AdminBookService {

  private final AdminBookRepository adminBookRepository;
  
  // 도서 등록
  public BookResponseDTO createBook(BookRequestDTO bookDTO) {
    Book book = Book.builder()
                  .title(bookDTO.getTitle())
                  .author(bookDTO.getAuthor())
                  .publisher(bookDTO.getPublisher())
                  .publishedDate(bookDTO.getPublishedDate())
                  .location(bookDTO.getLocation())
                  .description(bookDTO.getDescription())
                  .build();

    Book saveBook = adminBookRepository.save(book);

    return new BookResponseDTO(saveBook);
  }
}
