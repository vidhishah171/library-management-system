package com.books.librarymanagementsystem.exception;

public class BookNotFoundException extends BooksException {

  private static final long serialVersionUID = 1L;

  public BookNotFoundException(String message) {

    super(message);
  }

  public BookNotFoundException(final Throwable cause) {

    super(cause);
  }

  public BookNotFoundException(String message, final Throwable cause) {

    super(message, cause);
  }
}
