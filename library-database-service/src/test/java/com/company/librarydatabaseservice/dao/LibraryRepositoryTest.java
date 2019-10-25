package com.company.librarydatabaseservice.dao;

import com.company.librarydatabaseservice.model.Book;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class LibraryRepositoryTest {

    @Autowired
    private LibraryRepository repo;

    @BeforeEach
    void setUp() {
        repo.deleteAll();
    }

    @Test
    void shouldReturnObjectWithIdOnSave(){
        Book aBook = new Book(
                "123456789",
                "Good Book",
                "John Smith"
        );

        Book checkBook = repo.save(aBook);

        assertTrue(checkBook.getId() > 0);
    }

    @Test
    void shouldReturnBookProvidedId(){
        Book aBook = new Book(
                "123456789",
                "Good Book",
                "John Smith"
        );
        aBook = repo.save(aBook);

        Book checkBook = repo.findById(aBook.getId()).get();

        assertEquals(aBook, checkBook);
    }

    @Test
    void shouldReturnListofBooks(){
        Book aBook = new Book(
                "123456789",
                "Good Book",
                "John Smith"
        );
        aBook = repo.save(aBook);
        Book aBook2 = new Book(
                "342516",
                "Bad Book",
                "Smith John"
        );
        aBook2 = repo.save(aBook2);

        List<Book> expected = new ArrayList<>();
        expected.add(aBook);
        expected.add(aBook2);

        List<Book> checkList = repo.findAll();

        assertEquals(expected, checkList);
    }

    @Test
    void shouldGetNewValueAfterBookUpdate(){
        Book aBook = new Book(
                "123456789",
                "Good Book",
                "John Smith"
        );
        aBook = repo.save(aBook);

        String newTitle = "A Very Good Book";
        aBook.setTitle(newTitle);
        repo.save(aBook);

        Book checkBook = repo.findById(aBook.getId()).get();

        assertEquals(newTitle, checkBook.getTitle());
    }

    @Test
    void shouldNotExistAfterDeleteById(){
        Book aBook = new Book(
                "123456789",
                "Good Book",
                "John Smith"
        );
        aBook = repo.save(aBook);

        repo.deleteById(aBook.getId());

        assertFalse(repo.existsById(aBook.getId()));
    }
}