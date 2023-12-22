package com.lms.repo;

import com.lms.config.DatabaseConfig;
import com.lms.dto.BookRequest;
import com.lms.entity.Book;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

@Repository
public class BookRepository {

    @Autowired
    private DatabaseConfig databaseConnection;
    
    private static final Logger logger = LoggerFactory.getLogger(BookRepository.class);
    
    @PostConstruct
    public void createTableIfNotExists() {
    	Connection connection = null;
        try {
        	connection = databaseConnection.databaseConnection();
            String createTableQuery = "CREATE TABLE IF NOT EXISTS books (" +
                    "id INT AUTO_INCREMENT PRIMARY KEY," +
                    "title VARCHAR(255) NOT NULL," +
                    "author VARCHAR(255) NOT NULL" +
                    ")";
            try (Statement statement = connection.createStatement()) {
                statement.executeUpdate(createTableQuery);
                logger.error("Table created Successfully.");
            }
        } catch (SQLException e) {
            logger.error("SQL error creating table:", e);
        }
    }

    public void addBook(BookRequest bookRequest) {
        Connection connection = null;
        try {
            connection = databaseConnection.databaseConnection();
            boolean duplicateExists = checkIfDuplicateBook(connection, bookRequest);

            if (!duplicateExists) {
                addBookToDatabase(connection, bookRequest);
            } else {
                throw new IllegalArgumentException("Duplicate entry: Book with the same title and author already exists");
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public List<Book> getAllBooks() {
        Connection connection = null;
        try {
            connection = databaseConnection.databaseConnection();
            return getAllBooks(connection);
        } catch (SQLException e) {
            e.printStackTrace();
            return List.of(); // Return an empty list or handle the exception appropriately
        }
    }

    public Optional<Book> getBookById(Long id) {
        Connection connection = null;
        try {
            connection = databaseConnection.databaseConnection();
            return getBookById(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty(); // Return an empty optional or handle the exception appropriately
        }
    }

    public Optional<List<Book>> getBookByTitle(String title) {
        Connection connection = null;
        try {
            connection = databaseConnection.databaseConnection();
            return searchBooksByTitle(connection, title);
        } catch (SQLException e) {
            e.printStackTrace();
            return Optional.empty(); // Return an empty optional or handle the exception appropriately
        }
    }

    public boolean updateBook(Long id, BookRequest bookRequest) {
        try {
        	Connection connection = databaseConnection.databaseConnection();
            Optional<Book> existingBook = getBookById(connection, id);

            if (existingBook.isPresent()) {
                try {
                    boolean duplicateExists = checkIfDuplicateBook(connection, bookRequest);

                    if (!duplicateExists) {
                        updateBookInDatabase(connection, id, bookRequest);
                        return true; // Update successful
                    } else {
                    	return false;
                    }
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false; // Update failed
    }

    public void deleteBook(Long id) {
        Connection connection = null;
        try {
            connection = databaseConnection.databaseConnection();
            deleteBook(connection, id);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private boolean checkIfDuplicateBook(Connection connection, BookRequest bookRequest) throws SQLException {
        String query = "SELECT COUNT(*) FROM books WHERE title = ? AND author = ?";
        try {
        	PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookRequest.getTitle());
            preparedStatement.setString(2, bookRequest.getAuthor());

            ResultSet resultSet = preparedStatement.executeQuery();
            resultSet.next();
            int count = resultSet.getInt(1);

            return count > 0;
        } catch (SQLException e) {
            e.printStackTrace();
        }
		return false;
    }

    private void addBookToDatabase(Connection connection, BookRequest bookRequest) throws SQLException {
        String query = "INSERT INTO books (title, author) VALUES (?, ?)";
        try {
        	PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookRequest.getTitle());
            preparedStatement.setString(2, bookRequest.getAuthor());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private List<Book> getAllBooks(Connection connection) throws SQLException {
        List<Book> books = new ArrayList<>();

        try {
        	PreparedStatement preparedStatement = connection.prepareStatement("SELECT * FROM books");
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Book book = mapResultSetToBook(resultSet);
                books.add(book);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return books;
    }

    private Optional<Book> getBookById(Connection connection, Long id) throws SQLException {
        String query = "SELECT * FROM books WHERE id = ?";
        try {
        	PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);

            ResultSet resultSet = preparedStatement.executeQuery();
            if (resultSet.next()) {
                return Optional.of(mapResultSetToBook(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.empty();
    }

    private Optional<List<Book>> searchBooksByTitle(Connection connection, String title) throws SQLException {
        List<Book> books = new ArrayList<>();

        String query = "SELECT * FROM books WHERE title LIKE ?";
        try (PreparedStatement preparedStatement = connection.prepareStatement(query)) {
            preparedStatement.setString(1, "%" + title + "%");

            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                books.add(mapResultSetToBook(resultSet));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return Optional.of(books);
    }

    private void updateBookInDatabase(Connection connection, Long id, BookRequest bookRequest) throws SQLException {
        String query = "UPDATE books SET title = ?, author = ? WHERE id = ?";
        try {
        	PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setString(1, bookRequest.getTitle());
            preparedStatement.setString(2, bookRequest.getAuthor());
            preparedStatement.setLong(3, id);

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private void deleteBook(Connection connection, Long id) throws SQLException {
        String query = "DELETE FROM books WHERE id = ?";
        try {
        	PreparedStatement preparedStatement = connection.prepareStatement(query);
            preparedStatement.setLong(1, id);
            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private Book mapResultSetToBook(ResultSet resultSet) throws SQLException {
        Book book = new Book();
        book.setId(resultSet.getLong("id"));
        book.setTitle(resultSet.getString("title"));
        book.setAuthor(resultSet.getString("author"));
        return book;
    }

}
