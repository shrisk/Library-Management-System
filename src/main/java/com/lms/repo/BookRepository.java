package com.lms.repo;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class BookRepository {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public BookRepository(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
        createTableIfNotExists();
    }

    public void createTableIfNotExists() {
        jdbcTemplate.update("CREATE TABLE IF NOT EXISTS books (" +
                "id INT AUTO_INCREMENT PRIMARY KEY," +
                "title VARCHAR(255) NOT NULL," +
                "author VARCHAR(255) NOT NULL" +
                ")");
    }

    public void addBook(BookRequest bookRequest) {
        if (!checkIfDuplicateBook(bookRequest)) {
            jdbcTemplate.update("INSERT INTO books (title, author) VALUES (?, ?)",
                    bookRequest.getTitle(), bookRequest.getAuthor());
        } else {
            throw new IllegalArgumentException("Duplicate entry: Book with the same title and author already exists");
        }
    }

    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM books", new BeanPropertyRowMapper<>(Book.class));
    }

    public Optional<Book> getBookById(Long id) {
        List<Book> result = jdbcTemplate.query("SELECT * FROM books WHERE id = ?",
                new BeanPropertyRowMapper<>(Book.class), id);
        return result.isEmpty() ? Optional.empty() : Optional.of(result.get(0));
    }

    public Optional<List<Book>> getBookByTitle(String title) {
        List<Book> result = jdbcTemplate.query("SELECT * FROM books WHERE title LIKE ?",
                new BeanPropertyRowMapper<>(Book.class), "%" + title + "%");
        return result.isEmpty() ? Optional.empty() : Optional.of(result);
    }

    public boolean updateBook(Long id, BookRequest bookRequest) {
        Optional<Book> existingBook = getBookById(id);

        if (existingBook.isPresent()) {
            if (!checkIfDuplicateBook(bookRequest)) {
                jdbcTemplate.update("UPDATE books SET title = ?, author = ? WHERE id = ?",
                        bookRequest.getTitle(), bookRequest.getAuthor(), id);
                return true; // Update successful
            } else {
                return false; // Duplicate entry
            }
        }

        return false; // Update failed
    }

    public void deleteBook(Long id) {
        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);
    }

    private boolean checkIfDuplicateBook(BookRequest bookRequest) {
        int count = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM books WHERE title = ? AND author = ?",
                Integer.class, bookRequest.getTitle(), bookRequest.getAuthor());

        return count > 0;
    }
}
