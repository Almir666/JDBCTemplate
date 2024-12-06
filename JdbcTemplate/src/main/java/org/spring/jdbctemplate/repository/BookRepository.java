package org.spring.jdbctemplate.repository;

import org.spring.jdbctemplate.model.Book;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.util.List;


public interface BookRepository {
    RowMapper<Book> ROW_MAPPER = (ResultSet resultSet, int rowNum) -> {
        return new Book(resultSet.getLong("id"),
                resultSet.getString("title"),
                resultSet.getString("author"),
                resultSet.getString("publication_year"));
    };

    List<Book> getAllBooks();
    Book getBookByTitle(String title);
    Book addBook(Book book);
    Book updateBook(Book book);
    Book deleteBook(long id);
}
