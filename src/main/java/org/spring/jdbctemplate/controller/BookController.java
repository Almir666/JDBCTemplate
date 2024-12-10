package org.spring.jdbctemplate.controller;

import lombok.AllArgsConstructor;
import org.spring.jdbctemplate.model.Book;
import org.spring.jdbctemplate.service.BookService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/books")
@AllArgsConstructor
public class BookController {
    private final BookService bookService;

    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(bookService.getAllBooks());
    }

    @GetMapping("/{title}")
    public ResponseEntity<Book> getBookByTitle(@PathVariable String title) {
        return ResponseEntity.ok(bookService.getBookByTitle(title));
    }

    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        return new ResponseEntity<>(bookService.addBook(book), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Book> updateBook(@RequestBody Book book) {
        return ResponseEntity.ok(bookService.updateBook(book));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Book> deleteMapping(@PathVariable long id) {
        return ResponseEntity.ok(bookService.deleteBook(id));
    }
}
