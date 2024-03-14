package com.books.librarymanagementsystem.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.books.librarymanagementsystem.entity.Books;

@Repository
public interface BooksRepo extends JpaRepository<Books, Long> {

}
