package com.books.librarymanagementsystem.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.time.Year;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.books.librarymanagementsystem.entity.Books;
import com.books.librarymanagementsystem.exception.BookNotFoundException;
import com.books.librarymanagementsystem.exception.BooksException;
import com.books.librarymanagementsystem.repo.BooksRepo;
import com.books.librarymanagementsystem.service.impl.BookServiceImpl;

@ExtendWith(SpringExtension.class)
@RunWith(SpringRunner.class)
public class BooksServiceTest {

  @TestConfiguration
  static class EmployeeServiceImplTestContextConfiguration {

    @Bean
    public BooksService booksService() {

      return new BookServiceImpl();
    }
  }

  @Autowired
  private BooksService bookService;

  @MockBean
  private BooksRepo booksRepo;

  private final Books book = Books.builder()
      .author("Author")
      .title("Title")
      .publicationYear(Year.of(2022))
      .build();

  @Test
  public void addBook_shouldAddBook_whenValidBookGiven() throws BooksException {

    when(booksRepo.save(any(Books.class))).thenReturn(book);
    Books savedBook = bookService.addBook(book);
    assertEquals(savedBook, book);
    verify(booksRepo, times(1)).save(any(Books.class));
  }

  @Test
  public void getBook_shouldReturnBook_whenValidIdGiven() throws BooksException {

    when(booksRepo.findById(anyLong())).thenReturn(Optional.of(book));
    Books result = bookService.getBook(1L);
    assertEquals(book, result);
    verify(booksRepo, times(1)).findById(anyLong());
  }

  @Test
  public void getBook_shouldThrowException_whenNullIdGiven() {

    assertThrows(BooksException.class, () -> {
      bookService.getBook(null);
    });
  }

  @Test
  public void getAllBooks_shouldReturnAllBooks() {

    List<Books> booksList = new ArrayList<>();
    booksList.add(book);
    when(booksRepo.findAll()).thenReturn(booksList);
    List<Books> result = bookService.getAllBooks();
    assertEquals(1, result.size());
    assertEquals(book, result.get(0));
    verify(booksRepo, times(1)).findAll();
  }

  @Test
  public void updateBook_shouldUpdateBook_whenValidIdAndBookGiven() throws BooksException {

    when(booksRepo.findById(anyLong())).thenReturn(Optional.of(book));
    when(booksRepo.save(any(Books.class))).thenReturn(book);
    Books updatedBook = new Books();
    updatedBook.setTitle("Updated Title");
    updatedBook.setAuthor("Updated Author");
    updatedBook.setPublicationYear(Year.of(2023));
    Books result = bookService.updateBook(1L, updatedBook);
    assertEquals("Updated Title", result.getTitle());
    assertEquals("Updated Author", result.getAuthor());
    assertEquals(Year.of(2023), result.getPublicationYear());
    verify(booksRepo, times(1)).findById(anyLong());
    verify(booksRepo, times(1)).save(any(Books.class));
  }

  @Test
  public void updateBook_shouldThrowException_whenNullIdGiven() {

    assertThrows(BooksException.class, () -> {
      bookService.updateBook(null, book);
    });
  }

  @Test
  public void deleteBook_shouldDeleteBook_whenValidIdGiven() throws BooksException {

    when(booksRepo.existsById(anyLong())).thenReturn(true);
    String result = bookService.deleteBook(1L);
    assertEquals("Book with Id: 1 deleted successfully.", result);
    verify(booksRepo, times(1)).existsById(anyLong());
    verify(booksRepo, times(1)).deleteById(anyLong());
  }

  @Test
  public void deleteBook_shouldThrowException_whenNullIdGiven() {

    assertThrows(BooksException.class, () -> {
      bookService.deleteBook(null);
    });
  }

  @Test
  public void deleteBook_shouldThrowException_whenBookNotFound() {

    when(booksRepo.existsById(anyLong())).thenReturn(false);
    assertThrows(BookNotFoundException.class, () -> {
      bookService.deleteBook(1L);
    });
    verify(booksRepo, times(1)).existsById(anyLong());
    verify(booksRepo, times(0)).deleteById(anyLong());
  }
}
