package org.spring.jdbctemplate.service;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.jdbctemplate.model.Book;
import org.spring.jdbctemplate.repository.impl.BookRepositoryImpl;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class BookServiceTest {
    @InjectMocks
    private BookService bookService;

    @Mock
    private BookRepositoryImpl bookRepository;

    @Test
    void testGetAllBooks() {
        List<Book> mockBooks = List.of(
                new Book(1, "Harry Potter", "Joanne Rowling", "2001"),
                new Book(2, "Lord of the rings", "J.R.R. Tolkien", "2002")
        );

        when(bookRepository.getAllBooks()).thenReturn(mockBooks);

        List<Book> books = bookService.getAllBooks();

        assertEquals(2, books.size());
        assertEquals("Harry Potter", books.get(0).getTitle());
        verify(bookRepository, times(1)).getAllBooks();
    }

    @Test
    void testGetBookByTitle() {
        Book book = new Book(1, "Lord of the rings", "J.R.R. Tolkien", "2002");

        when(bookRepository.getBookByTitle("Lord of the rings")).thenReturn(book);

        Book result = bookService.getBookByTitle("Lord of the rings");

        assertEquals("J.R.R. Tolkien", result.getAuthor());

        verify(bookRepository, times(1)).getBookByTitle("Lord of the rings");
    }

    @Test
    void testAddBook() {
        Book book = new Book(1, "Lord of the rings", "J.R.R. Tolkien", "2002");

        when(bookRepository.addBook(book)).thenReturn(book);

        Book result = bookService.addBook(book);

        assertEquals("Lord of the rings", result.getTitle());

        verify(bookRepository, times(1)).addBook(book);
    }

    @Test
    void testUpdateBook() {
        Book book = new Book(1, "Lord of the rings", "Lord of the rings", "2002");

        when(bookRepository.updateBook(book)).thenReturn(book);

        Book result = bookService.updateBook(book);

        assertEquals("Lord of the rings", result.getTitle());

        verify(bookRepository, times(1)).updateBook(book);
    }

    @Test
    void testDeleteBook() {
        Book book = new Book(1, "Lord of the rings", "Lord of the rings", "2002");

        when(bookRepository.deleteBook(1)).thenReturn(book);

        Book result = bookService.deleteBook(1);


        assertEquals("Lord of the rings", result.getTitle());

        verify(bookRepository, times(1)).deleteBook(1);
    }
}
