package com.lms.service;

import java.util.List;
import java.util.Optional;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;

public interface LibraryService {

    void addBook(BookRequest bookRequest);

    List<Book> getAllBooks();

    Optional<Book> getBookById(Long id);

    boolean updateBook(Long id, BookRequest bookRequest);

    void deleteBook(Long id);

    Optional<List<Book>> getBookByTitle(String title);
}

