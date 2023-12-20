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
        // Check if a book with the same title and author combination already exists
        boolean duplicateExists = this.checkIfDuplicateBook(bookRequest);

        if (!duplicateExists) {
            // If no duplicate is found, add the new book to the list
            books.add(new Book(bookRequest.getTitle(), bookRequest.getAuthor()));
        } else {
            throw new IllegalArgumentException("Duplicate entry: Book with the same title and author already exists");
        }
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
        Optional<Book> existingBook = getBookById(id);

        existingBook.ifPresent(book -> {
            // Check if the updated title and author combination already exists
        	boolean duplicateExists = this.checkIfDuplicateBook(bookRequest);

            if (!duplicateExists) {
                // If no duplicate is found, update the existing book
                book.setTitle(bookRequest.getTitle());
                book.setAuthor(bookRequest.getAuthor());
            } else {
                throw new IllegalArgumentException("Duplicate entry: Book with the same title and author already exists");
            }
        });
    }

    @Override
    public void deleteBook(Long id) {
        books.removeIf(book -> book.getId().equals(id));
    }
    
    private boolean checkIfDuplicateBook(BookRequest bookRequest) {
    	 boolean duplicateExists = books.stream()
                 .anyMatch(book -> book.getTitle().equalsIgnoreCase(bookRequest.getTitle())
                         && book.getAuthor().equalsIgnoreCase(bookRequest.getAuthor()));
    	 return duplicateExists;
    }
}

