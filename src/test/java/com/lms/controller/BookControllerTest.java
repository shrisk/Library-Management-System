package com.lms.controller;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;
import com.lms.service.BookService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class BookControllerTest {

    @Mock
    private BookService bookService;

    @InjectMocks
    private BookController bookController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddBook() {
        BookRequest bookRequest = new BookRequest();
        ResponseEntity<String> responseEntity = bookController.addBook(bookRequest);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        verify(bookService, times(1)).addBook(any(BookRequest.class));
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Arrays.asList(new Book(), new Book());
        when(bookService.getAllBooks()).thenReturn(books);

        ResponseEntity<List<Book>> responseEntity = bookController.getAllBooks();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(books, responseEntity.getBody());
    }

    @Test
    void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        when(bookService.getBookById(bookId)).thenReturn(Optional.of(book));

        ResponseEntity<Book> responseEntity = bookController.getBook(bookId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals(book, responseEntity.getBody());
    }

    @Test
    void testUpdateBookSuccess() {
        Long bookId = 1L;
        BookRequest bookRequest = new BookRequest();
        when(bookService.updateBook(bookId, bookRequest)).thenReturn(true);

        ResponseEntity<String> responseEntity = bookController.updateBook(bookId, bookRequest);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertEquals("Book updated successfully", responseEntity.getBody());
    }

    @Test
    void testUpdateBookFailure() {
        Long bookId = 1L;
        BookRequest bookRequest = new BookRequest();
        when(bookService.updateBook(bookId, bookRequest)).thenReturn(false);

        ResponseEntity<String> responseEntity = bookController.updateBook(bookId, bookRequest);

        assertEquals(HttpStatus.BAD_REQUEST, responseEntity.getStatusCode());
        assertEquals("Book duplicate/not found", responseEntity.getBody());
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        ResponseEntity<String> responseEntity = bookController.deleteBook(bookId);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(bookService, times(1)).deleteBook(bookId);
    }
}


