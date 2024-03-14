package com.books.librarymanagementsystem.exception;

public class BooksException extends Exception {

  private static final long serialVersionUID = 1L;

  public BooksException(String message) {

    super(message);
  }

  public BooksException(final Throwable cause) {

    super(cause);
  }

  public BooksException(String message, final Throwable cause) {

    super(message, cause);
  }
}
