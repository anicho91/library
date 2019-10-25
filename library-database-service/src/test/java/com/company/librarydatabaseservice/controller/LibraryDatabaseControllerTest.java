package com.company.librarydatabaseservice.controller;

import com.company.librarydatabaseservice.dao.LibraryRepository;
import com.company.librarydatabaseservice.model.Book;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.tomcat.jni.Library;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import javax.sql.DataSource;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest
class LibraryDatabaseControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private LibraryDatabaseController controller;

    @MockBean
    private LibraryRepository repo;

    @MockBean
    private DataSource dataSource;

    @InjectMocks
    private LibraryDatabaseControllerTest test;

    private ObjectMapper mapper = new ObjectMapper();

    @BeforeEach
    void setUp() {
    }

    @Test
    void shouldReturnFullBookOnSave() throws Exception {
        Book aBook = new Book(
                "123456789",
                "Good Book",
                "John Smith"
        );
        String jsonInput = mapper.writeValueAsString(aBook);

        Book savedBook = new Book(
                1,
                "123456789",
                "Good Book",
                "John Smith"
        );
        String jsonOutput = mapper.writeValueAsString(savedBook);

        when(repo.save(aBook)).thenReturn(savedBook);

        this.mockMvc.perform(post("/book")
            .content(jsonInput)
            .contentType(MediaType.APPLICATION_JSON))
            .andDo(print())
            .andExpect(status().isCreated())
            .andExpect(content().json(jsonOutput)
        );


    }

    @Test
    void shouldUseSaveMethodWhenUpdateEndpointIsHit() throws Exception {
        Book updatedBook = new Book(
                1,
                "123456789",
                "A Very Good Book",
                "John Smith"
        );
        String jsonInput = mapper.writeValueAsString(updatedBook);

        when(repo.save(updatedBook)).thenReturn(updatedBook);

        controller.updateBook(updatedBook);

        verify(repo,times(1)).save(updatedBook);
    }

    @Test
    void getAllBooks() throws Exception {
        Book savedBook = new Book(
                1,
                "123456789",
                "Good Book",
                "John Smith"
        );

        Book savedBook2 = new Book(
                2,
                "342516",
                "Bad Book",
                "Smith John"
        );
        List<Book> list = new ArrayList<>();
        list.add(savedBook);
        list.add(savedBook2);
        String output = mapper.writeValueAsString(list);

        when(repo.findAll()).thenReturn(list);

        this.mockMvc.perform(get("/book"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(output));
    }

    @Test
    void getBook() throws Exception {
        Book savedBook2 = new Book(
                2,
                "342516",
                "Bad Book",
                "Smith John"
        );
        String output = mapper.writeValueAsString(savedBook2);

        when(repo.getOne(2)).thenReturn(savedBook2);

        this.mockMvc.perform(get("/book/2"))
                .andDo(print())
                .andExpect(status().isFound())
                .andExpect(content().json(output)
        );

    }

    @Test
    void deleteBook() {

        controller.deleteBook(1);

        verify(repo,times(1)).deleteById(1);
    }

}