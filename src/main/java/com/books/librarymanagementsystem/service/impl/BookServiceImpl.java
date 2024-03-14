package com.books.librarymanagementsystem.service.impl;

import java.time.Year;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import com.books.librarymanagementsystem.entity.Books;
import com.books.librarymanagementsystem.entity.Books.BooksBuilder;
import com.books.librarymanagementsystem.exception.BookNotFoundException;
import com.books.librarymanagementsystem.exception.BooksException;
import com.books.librarymanagementsystem.repo.BooksRepo;
import com.books.librarymanagementsystem.service.BooksService;
import com.books.librarymanagementsystem.util.LibraryUtil;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class BookServiceImpl implements BooksService {

  @Autowired
  private BooksRepo booksRepo;

  @Override
  public Books addBook(Books book) throws BooksException {

    if (Objects.nonNull(book)) {
      log.info(LibraryUtil.logFormat("Adding the book with title: {} to database.",
          new String[] {book.getTitle()}));
      book = this.booksRepo.save(book);
      return book;
    } else {
      throw new BooksException("Book object must not be null.");
    }
  }

  @Override
  public Books addBook(String author, String title, Year publicationYear) throws BooksException {

    Assert.hasLength(author, "Book Author must not be null.");
    Assert.hasLength(title, "Book Title must not be null.");
    Assert.notNull(publicationYear, "Book publication year must not be null.");
    log.info(LibraryUtil.logFormat("Adding the book with title: {} to database.",
        new String[] {title}));
    BooksBuilder booksBuilder = Books.builder()
        .author(author)
        .title(title)
        .publicationYear(publicationYear);
    Books book = this.booksRepo.save(booksBuilder.build());
    return book;
  }

  @Override
  public Books getBook(Long id) throws BooksException {

    if (Objects.nonNull(id)) {
      log.info(LibraryUtil.logFormat("Fetching the book with Id: {} from database.",
          new String[] {String.valueOf(id)}));
      Optional<Books> bookOptional = this.booksRepo.findById(id);
      if (bookOptional.isPresent()) {
        return bookOptional.get();
      } else {
        throw new BookNotFoundException("Unable to find book with Id: " + id + ".");
      }
    } else {
      throw new BooksException("Id must not be null for fetching books.");
    }
  }

  @Override
  public List<Books> getAllBooks() {

    log.info(LibraryUtil.logFormat("Fetching all the books from database.", null));
    return this.booksRepo.findAll();
  }

  @Override
  public Books updateBook(Long id, Books book) throws BooksException {

    try {
      Books oldBook = this.getBook(id);
      log.info(LibraryUtil.logFormat("Updating the book with Id: {} to database.",
          new String[] {String.valueOf(id)}));
      oldBook.setAuthor(book.getAuthor());
      oldBook.setTitle(book.getTitle());
      oldBook.setPublicationYear(book.getPublicationYear());
      return this.booksRepo.save(oldBook);
    } catch (BooksException e) {
      throw new BooksException(
          "Unable to update book with Id: " + id + "due to reason: " + e.getMessage());
    }
  }

  @Override
  public String deleteBook(Long id) throws BooksException {

    if (Objects.nonNull(id)) {
      if (this.booksRepo.existsById(id)) {
        this.booksRepo.deleteById(id);
        return "Book with Id: " + id + " deleted successfully.";
      } else {
        throw new BookNotFoundException("Unable to find book with Id: " + id + ".");
      }
    } else {
      throw new BooksException("Id must not be null for fetching books.");
    }
  }
}
