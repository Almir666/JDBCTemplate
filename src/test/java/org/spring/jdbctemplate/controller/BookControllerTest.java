package org.spring.jdbctemplate.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.spring.jdbctemplate.model.Book;
import org.spring.jdbctemplate.repository.impl.BookRepositoryImpl;
import org.spring.jdbctemplate.service.BookService;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
public class BookControllerTest {

    @Mock
    private BookService bookService;

    @Mock
    private BookRepositoryImpl bookRepository;

    @InjectMocks
    private BookController bookController;

    private MockMvc mockMvc;


    @BeforeEach
    void setUp() {
        mockMvc = MockMvcBuilders.standaloneSetup(bookController).build();
    }

    @Test
    void testGetAllBooks() throws Exception {
        List<Book> books = Arrays.asList(
                new Book(1, "Harry Potter", "Joanne Rowling", "2001"),
                new Book(2, "Lord of the rings", "J.R.R. Tolkien", "2002")
        );

        when(bookService.getAllBooks()).thenReturn(books);

        mockMvc.perform(get("/books"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].title").value("Harry Potter"));

        verify(bookService, times(1)).getAllBooks();
    }

    @Test
    void testGetBookByTitle() throws Exception {
        Book book = new Book(1, "Lord of the rings", "J.R.R. Tolkien", "2002");
        when(bookService.getBookByTitle("Lord of the rings")).thenReturn(book);

        mockMvc.perform(get("/books/{title}", "Lord of the rings"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Lord of the rings"))
                .andExpect(jsonPath("$.author").value("J.R.R. Tolkien"));

        verify(bookService, times(1)).getBookByTitle("Lord of the rings");
    }

    @Test
    void testAddBook() throws Exception {
        String jsonBook = """
                {
                "title": "Lord of the rings",
                "author": "J.R.R. Tolkien",
                "publicationYear": "2002"
                }
                """;
        Book book = new Book(1, "Lord of the rings", "J.R.R. Tolkien", "2002");
        when(bookService.addBook(Mockito.any(Book.class))).thenReturn(book);

        mockMvc.perform(post("/books")
                .contentType(MediaType.APPLICATION_JSON)
                .content(jsonBook))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.title").value("Lord of the rings"));

        verify(bookService, times(1)).addBook(any(Book.class));
    }

    @Test
    void testUpdateBook() throws Exception {
        String jsonBook = """
                {
                "title": "Lord of the rings",
                "author": "J.R.R. Tolkien",
                "publicationYear": "2002"
                }
                """;
        Book book = new Book(1, "Lord of the rings", "J.R.R. Tolkien", "2002");
        when(bookService.updateBook(Mockito.any(Book.class))).thenReturn(book);

        mockMvc.perform(put("/books")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(jsonBook))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Lord of the rings"));

        verify(bookService, times(1)).updateBook(any(Book.class));
    }

    @Test
    void testDeleteBook() throws Exception {
        Book book = new Book(1, "Lord od the rings", "J.R.R. Tolkien", "2002");
        when(bookService.deleteBook(1L)).thenReturn(book);

        mockMvc.perform(delete("/books/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.title").value("Lord od the rings"));

        verify(bookService, times(1)).deleteBook(1L);
    }
}
