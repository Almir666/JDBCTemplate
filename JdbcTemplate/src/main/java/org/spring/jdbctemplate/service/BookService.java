package org.spring.jdbctemplate.service;

import lombok.AllArgsConstructor;
import org.spring.jdbctemplate.model.Book;
import org.spring.jdbctemplate.repository.impl.BookRepositoryImpl;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookService {
    private final BookRepositoryImpl bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.getAllBooks();
    }

    public Book getBookByTitle(String title) {
        return bookRepository.getBookByTitle(title);
    }

    public Book addBook(Book book) {
        return bookRepository.addBook(book);
    }

    public Book updateBook(Book book) {
        return bookRepository.updateBook(book);
    }

    public Book deleteBook(long id) {
        return bookRepository.deleteBook(id);
    }
}
