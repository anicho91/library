package com.company.librarydatabaseservice.dao;

import com.company.librarydatabaseservice.model.Book;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LibraryRepository extends JpaRepository<Book,Integer> {
}
