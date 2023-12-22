package com.lms.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;
import com.lms.repo.BookRepository;

import java.util.List;
import java.util.Optional;

@Service
public class LibraryServiceImpl implements LibraryService {

    @Autowired
    private BookRepository bookRepo;

    @Override
    public void addBook(BookRequest bookRequest) {
        bookRepo.addBook(bookRequest);
    }

    @Override
    public List<Book> getAllBooks() {
        return bookRepo.getAllBooks();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        return bookRepo.getBookById(id);
    }

    @Override
    public boolean updateBook(Long id, BookRequest bookRequest) {
        return bookRepo.updateBook(id, bookRequest);
    }

    @Override
    public void deleteBook(Long id) {
        bookRepo.deleteBook(id);
    }
    
    @Override
    public Optional<List<Book>> getBookByTitle(String title) {
    	return bookRepo.getBookByTitle(title);
    }
}
