package com.lms.service;

import java.util.List;
import java.util.Optional;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;

public interface LibraryService {

    void addBook(BookRequest bookRequest);

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    void updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);
}

