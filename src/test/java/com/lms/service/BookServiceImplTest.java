package com.lms.service;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;
import com.lms.entity.Person;
import com.lms.repo.BookRepository;
import com.lms.repo.PersonRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class BookServiceImplTest {

    @Mock
    private BookRepository bookRepository;

    @Mock
    private PersonRepository personRepository;

    @InjectMocks
    private BookServiceImpl bookService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void testAddBook() {
        BookRequest bookRequest = new BookRequest();
        bookService.addBook(bookRequest);
        verify(bookRepository, times(1)).addBook((bookRequest));
    }

    @Test
    void testGetAllBooks() {
        List<Book> books = Collections.singletonList(new Book());
        when(bookRepository.getAllBooks()).thenReturn(books);

        List<Book> result = bookService.getAllBooks();

        assertEquals(books, result);
    }

    @Test
    void testGetBookById() {
        Long bookId = 1L;
        Book book = new Book();
        when(bookRepository.getBookById(bookId)).thenReturn(Optional.of(book));

        Optional<Book> result = bookService.getBookById(bookId);

        assertTrue(result.isPresent());
        assertEquals(book, result.get());
    }

    @Test
    void testUpdateBookSuccess() {
        Long bookId = 1L;
        BookRequest bookRequest = new BookRequest();

        // Mocking the PersonRepository to return a non-null Person
        Person borrower = new Person();
        borrower.setId(1L);
        bookRequest.setBorrower(borrower);
        when(personRepository.getPersonById(anyLong())).thenReturn(Optional.of(borrower));
        when(bookRepository.updateBook((bookId), (bookRequest))).thenReturn(true);

        boolean result = bookService.updateBook(bookId, bookRequest);

        assertTrue(result);

        // Verify that getPersonById is called once with the specified borrower.getId()
        verify(personRepository, times(1)).getPersonById((borrower.getId()));
        verify(bookRepository, times(1)).updateBook((bookId), (bookRequest));
    }

    @Test
    void testUpdateBookFailure() {
        Long bookId = 1L;
        
        BookRequest bookRequest = new BookRequest();
        // Mocking the PersonRepository to return a non-null Person
        Person borrower = new Person();
        borrower.setId(1L);
        bookRequest.setBorrower(borrower);
        when(personRepository.getPersonById(anyLong())).thenReturn(Optional.of(borrower));

        boolean result = bookService.updateBook(bookId, bookRequest);

        assertFalse(result);

        // Verify that getPersonById is called once with the specified borrower.getId()
        verify(personRepository, times(1)).getPersonById((Optional.ofNullable(bookRequest.getBorrower()).map(Person::getId).orElse(null)));
    }

    @Test
    void testDeleteBook() {
        Long bookId = 1L;
        bookService.deleteBook(bookId);
        verify(bookRepository, times(1)).deleteBook((bookId));
    }

    @Test
    void testGetNumberOfBooksBorrowed() {
        when(bookRepository.getNumberOfBooksBorrowed()).thenReturn(10L);

        Long result = bookService.getNumberOfBooksBorrowed();

        assertEquals(10L, result);
    }

}

