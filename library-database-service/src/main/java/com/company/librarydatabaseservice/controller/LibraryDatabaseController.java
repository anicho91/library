package com.company.librarydatabaseservice.controller;

import com.company.librarydatabaseservice.dao.LibraryRepository;
import com.company.librarydatabaseservice.model.Book;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/book")
public class LibraryDatabaseController {

    @Autowired
    private LibraryRepository repo;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Book addBook(@RequestBody @Valid Book book){
        return repo.save(book);
    }

    @PutMapping
    @ResponseStatus(HttpStatus.OK)
    public void updateBook(@RequestBody @Valid Book book){
        repo.save(book);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.FOUND)
    public List<Book> getAllBooks(){
        return repo.findAll();
    }

    @GetMapping(value = "/{bookId}")
    @ResponseStatus(HttpStatus.FOUND)
    public Book getBook(@PathVariable Integer bookId){
        return repo.getOne(bookId);
    }

    @DeleteMapping(value = "/{bookId}")
    @ResponseStatus(HttpStatus.OK)
    public void deleteBook(@PathVariable Integer bookId){
        repo.deleteById(bookId);
    }
}
