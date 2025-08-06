package com.jelee.librarymanagementsystem.domain.admin.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jelee.librarymanagementsystem.domain.admin.entity.Book;

public interface AdminBookRepository extends JpaRepository<Book, Long> {
}
