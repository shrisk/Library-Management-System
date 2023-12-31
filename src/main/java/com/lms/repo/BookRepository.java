package com.lms.repo;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    private static final String INSERT_BOOK_QUERY = "INSERT INTO book (title, author) VALUES (?, ?)";
    private static final String SELECT_ALL_BOOKS_QUERY = "SELECT * FROM book";
    private static final String SELECT_BOOK_BY_ID_QUERY = "SELECT * FROM book WHERE id = ?";
    private static final String SELECT_BOOK_BY_TITLE_QUERY = "SELECT * FROM book WHERE title LIKE ?";
    private static final String UPDATE_BOOK_QUERY = "UPDATE book SET title = ?, author = ?, borrower_id = ? WHERE id = ?";
    private static final String DELETE_BOOK_QUERY = "DELETE FROM book WHERE id = ?";
    private static final String COUNT_DUPLICATE_BOOK_QUERY = "SELECT COUNT(*) FROM book WHERE title = ? AND author = ?";
    private static final String GET_NUMBER_OF_BOOKS_BORROWED_QUERY = "SELECT COUNT(*) FROM book WHERE borrower_id IS NOT NULL";

    public void addBook(BookRequest bookRequest) {
        if (!checkIfDuplicateBook(bookRequest)) {
            jdbcTemplate.update(INSERT_BOOK_QUERY, bookRequest.getTitle(), bookRequest.getAuthor());
        } else {
            logger.error("Duplicate entry: Book with the same title and author already exists");
            throw new IllegalArgumentException("Duplicate entry: Book with the same title and author already exists");
        }
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query(SELECT_ALL_BOOKS_QUERY, new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> getBookById(Long id) {
        List<Book> result = jdbcTemplate.query(SELECT_BOOK_BY_ID_QUERY, new BeanPropertyRowMapper<>(Book.class), id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public Optional<List<Book>> getBookByTitle(String title) {
        List<Book> result = jdbcTemplate.query(SELECT_BOOK_BY_TITLE_QUERY, new BeanPropertyRowMapper<>(Book.class), "%" + title + "%");
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    public boolean updateBook(Long id, BookRequest bookRequest) {
        Optional<Book> existingBook = getBookById(id);

        if (existingBook.isPresent()) {
            if (!checkIfDuplicateBook(bookRequest)) {
                jdbcTemplate.update(UPDATE_BOOK_QUERY, bookRequest.getTitle(), bookRequest.getAuthor(), bookRequest.getBorrower().getId(), id);
                return true; // Update successful
            } else {
                logger.error("Duplicate entry: Book with the same title and author already exists");
                return false; // Duplicate entry
            }
        }

        return false; // Update failed
    }

    public void deleteBook(Long id) {
        jdbcTemplate.update(DELETE_BOOK_QUERY, id);
    }

    private boolean checkIfDuplicateBook(BookRequest bookRequest) {
        int count = jdbcTemplate.queryForObject(COUNT_DUPLICATE_BOOK_QUERY, Integer.class, bookRequest.getTitle(), bookRequest.getAuthor());
        return count > 0;
    }

    public Long getNumberOfBooksBorrowed() {
        return jdbcTemplate.queryForObject(GET_NUMBER_OF_BOOKS_BORROWED_QUERY, Long.class);
    }
}
