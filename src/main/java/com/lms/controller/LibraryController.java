package com.lms.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.lms.dto.BookRequest;
import com.lms.entity.Book;
import com.lms.service.LibraryService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/library")
public class LibraryController {

	@Autowired
    private LibraryService libraryService;

    @PostMapping("/addBook")
    public ResponseEntity<String> addBook(@RequestBody BookRequest bookRequest) {
        libraryService.addBook(bookRequest);
        return new ResponseEntity<>("Book added successfully", HttpStatus.CREATED);
    }

    @GetMapping("/getAllBooks")
    public ResponseEntity<List<Book>> getAllBooks() {
        List<Book> books = libraryService.getAllBooks();
        return new ResponseEntity<>(books, HttpStatus.OK);
    }

    @GetMapping("/getBook/id/{id}")
    public ResponseEntity<Book> getBook(@PathVariable Long id) {
        return libraryService.getBookById(id)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PutMapping("/updateBook/{id}")
    public ResponseEntity<String> updateBook(@PathVariable Long id, @RequestBody BookRequest bookRequest) {
        boolean update = libraryService.updateBook(id, bookRequest);
        if (update) {
        	return new ResponseEntity<>("Book updated successfully", HttpStatus.OK);
        } else {
        	return new ResponseEntity<>("Book duplicate/not found", HttpStatus.BAD_REQUEST);
        }
        
    }

    @DeleteMapping("/deleteBook/{id}")
    public ResponseEntity<String> deleteBook(@PathVariable Long id) {
        libraryService.deleteBook(id);
        return new ResponseEntity<>("Book deleted successfully", HttpStatus.OK);
    }
    
    @GetMapping("/getBook/title/{title}")
    public ResponseEntity<List<Book>> getBooksByTitle(@PathVariable String title) {
       return libraryService.getBookByTitle(title)
                .map(book -> new ResponseEntity<>(book, HttpStatus.OK))
                .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}

