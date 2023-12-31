package com.lms.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;
import com.lms.entity.Person;
import com.lms.repo.BookRepository;
import com.lms.repo.PersonRepository;

import java.util.List;
import java.util.Optional;

@Service
public class BookServiceImpl implements BookService {

    private static final Logger logger = LoggerFactory.getLogger(BookServiceImpl.class);

    @Autowired
    private BookRepository bookRepo;
    
    @Autowired
    private PersonRepository personRepo;

    @Override
    public void addBook(BookRequest bookRequest) {
        logger.info("Adding a new book: {}", bookRequest);
        bookRepo.addBook(bookRequest);
        logger.info("Book added successfully.");
    }

    @Override
    public List<Book> getAllBooks() {
        logger.info("Retrieving all books.");
        return bookRepo.getAllBooks();
    }

    @Override
    public Optional<Book> getBookById(Long id) {
        logger.info("Retrieving book by ID: {}", id);
        return bookRepo.getBookById(id);
    }

    @Override
    public boolean updateBook(Long id, BookRequest bookRequest) {
        logger.info("Updating book with ID {}: {}", id, bookRequest);
        
        if (bookRequest.getBorrower().getId() != null) {
        	Optional<Person> optionalPerson = personRepo.getPersonById(bookRequest.getBorrower().getId());
        	Person borrower = new Person();
        	borrower.setId(optionalPerson.get().getId());
        	borrower.setName(optionalPerson.get().getName());
        	borrower.setEmail(optionalPerson.get().getEmail());
        	bookRequest.setBorrower(borrower);
        	 if (optionalPerson.isEmpty()) {
        		 logger.warn("Failed to update book. Book borrower not found.");
        		 return false;
        	 }
        }
        
        boolean result = bookRepo.updateBook(id, bookRequest);
        if (result) {
            logger.info("Book updated successfully.");
        } else {
            logger.warn("Failed to update book. Book not found or duplicate entry.");
        }
        return result;
    }

    @Override
    public void deleteBook(Long id) {
        logger.info("Deleting book with ID: {}", id);
        bookRepo.deleteBook(id);
        logger.info("Book deleted successfully.");
    }

    @Override
    public Optional<List<Book>> getBookByTitle(String title) {
        logger.info("Retrieving books by title: {}", title);
        return bookRepo.getBookByTitle(title);
    }

    @Override
    public Long getNumberOfBooksBorrowed() {
    	Long count = bookRepo.getNumberOfBooksBorrowed();
    	logger.info("Number Of Books Borrowed: {}", count);
    	return count;
    }
}
