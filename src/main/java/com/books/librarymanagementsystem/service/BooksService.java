package com.books.librarymanagementsystem.service;

import java.time.Year;
import java.util.List;

import com.books.librarymanagementsystem.entity.Books;
import com.books.librarymanagementsystem.exception.BooksException;

public interface BooksService {

  /**
   * @author Vidhi_s Method to add new book.
   * 
   * @param book
   * @return
   * @throws BooksException
   */
  Books addBook(Books book) throws BooksException;

  /**
   * @author Vidhi_s Method to add new book by parameters.
   * 
   * @param author
   * @param title
   * @param publicationYear
   * @return
   * @throws BooksException
   */
  Books addBook(String author, String title, Year publicationYear) throws BooksException;

  /**
   * @author Vidhi_s Method to get book by id.
   * 
   * @param id
   * @return
   * @throws BooksException
   */
  Books getBook(Long id) throws BooksException;

  /**
   * @author Vidhi_s Method to update book by id.
   * 
   * @param id
   * @param book
   * @return
   * @throws BooksException
   */
  Books updateBook(Long id, Books book) throws BooksException;

  /**
   * @author Vidhi_s Method to delete book by id.
   * 
   * @param id
   * @return
   * @throws BooksException
   */
  String deleteBook(Long id) throws BooksException;

  /**
   * @author Vidhi_s Method to fetch all the books.
   * 
   * @return
   */
  List<Books> getAllBooks();
}
