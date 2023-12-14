package com.lms.service;

import org.springframework.stereotype.Service;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    private final List<Book> books = new ArrayList<>();

    @Override
    public void addBook(BookRequest bookRequest) {
        books.add(new Book(bookRequest.getTitle(), bookRequest.getAuthor()));
    }

    @Override
    public List<Book> getAllBooks() {
        return new ArrayList<>(books);
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return books.stream()
                .filter(book -> book.getId().equals(id))
                .findFirst();
    }

    @Override
    public void updateBook(Long id, BookRequest bookRequest) {
        getBookById(id).ifPresent(existingBook -> {
            existingBook.setTitle(bookRequest.getTitle());
            existingBook.setAuthor(bookRequest.getAuthor());
        });
    }

    @Override
    public void deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
}

