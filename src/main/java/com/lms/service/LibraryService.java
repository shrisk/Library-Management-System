package com.lms.service;

import com.lms.dto.BookCountByTitle;
import com.lms.dto.BookRequest;
import com.lms.entity.Book;

import java.util.List;
import java.util.Optional;

/**
 * Service interface for managing books in the library.
 */
public interface LibraryService {

    /**
     * Add a new book to the library.
     *
     * @param bookRequest The request containing book information.
     */
    void addBook(BookRequest bookRequest);

    /**
     * Get a list of all books in the library.
     *
     * @return A list of books.
     */
    List<Book> getAllBooks();

    /**
     * Get a book by its unique identifier.
     *
     * @param id The unique identifier of the book.
     * @return An Optional containing the book if found, otherwise empty.
     */
    Optional<Book> getBookById(Long id);

    /**
     * Update the information of an existing book in the library.
     *
     * @param id           The unique identifier of the book to update.
     * @param bookRequest The request containing updated book information.
     * @return true if the update was successful, false if the book was not found or if it would result in a duplicate entry.
     */
    boolean updateBook(Long id, BookRequest bookRequest);

    /**
     * Delete a book from the library by its unique identifier.
     *
     * @param id The unique identifier of the book to delete.
     */
    void deleteBook(Long id);

    /**
     * Get a list of books matching the given title.
     *
     * @param title The title to search for.
     * @return An Optional containing a list of books if found, otherwise empty.
     */
    Optional<List<Book>> getBookByTitle(String title);
    
    /**
     * Get counts of books grouped by title.
     *
     * @return List of {@link BookCountByTitle} representing counts of books.
     */
    List<BookCountByTitle> getCountsByTitle();
}
