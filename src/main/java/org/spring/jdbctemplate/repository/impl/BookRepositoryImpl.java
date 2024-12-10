package org.spring.jdbctemplate.repository.impl;

import lombok.AllArgsConstructor;
import org.spring.jdbctemplate.model.Book;
import org.spring.jdbctemplate.repository.BookRepository;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
@AllArgsConstructor
public class BookRepositoryImpl implements BookRepository {
    private final JdbcTemplate jdbcTemplate;


    @Override
    public List<Book> getAllBooks() {
        return jdbcTemplate.query("SELECT * FROM books", ROW_MAPPER);
    }

    @Override
    public Book getBookByTitle(String title) {
        Book book = new Book();
        try {
            book = jdbcTemplate.queryForObject("SELECT * FROM books WHERE title=?", new Object[]{title}, ROW_MAPPER);
        } catch (DataAccessException e) {
            System.out.println(e.getMessage());
        }
        return book;
    }

    @Override
    public Book addBook(Book book) {
        jdbcTemplate.update("INSERT INTO books (title, author, publication_year) VALUES (?, ?, ?)",
                book.getTitle(), book.getAuthor(), book.getPublicationYear());

        return book;
    }

    @Override
    public Book updateBook(Book book) {
        jdbcTemplate.update("UPDATE books SET title = ?, author = ?, publication_year = ? WHERE id = ?",
                book.getTitle(), book.getAuthor(), book.getPublicationYear(), book.getId());

        return book;
    }

    @Override
    @Transactional
    public Book deleteBook(long id) {
        Book book = jdbcTemplate.queryForObject("SELECT * FROM books WHERE id=?", new Object[]{id}, ROW_MAPPER);

        jdbcTemplate.update("DELETE FROM books WHERE id = ?", id);

        return book;
    }
}
