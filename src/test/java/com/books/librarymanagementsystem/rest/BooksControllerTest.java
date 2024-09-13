package com.books.librarymanagementsystem.rest;

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

import org.junit.Test;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;

import com.books.librarymanagementsystem.bo.GenericResponse;
import com.books.librarymanagementsystem.entity.Books;
import com.books.librarymanagementsystem.exception.BooksException;
import com.books.librarymanagementsystem.service.BooksService;

@RunWith(SpringRunner.class)
@ExtendWith(SpringExtension.class)
public class BooksControllerTest {

  @TestConfiguration
  static class BooksControllerTestContextConfiguration {

    @Bean
    public BooksController booksController() {

      return new BooksController();
    }
  }

  @Autowired
  private BooksController booksController;

  @MockBean
  private BooksService booksService;

  private final Books book = Books.builder()
      .author("Author")
      .title("Title")
      .publicationYear(Year.of(2022))
      .build();

  @BeforeAll
  public static void beforeAll() {

    MockitoAnnotations.openMocks(BooksControllerTest.class);
  }

  @Test
  public void addBook_shouldAddBook_whenValidBookGiven() throws BooksException {

    when(booksService.addBook(any(Books.class))).thenReturn(book);
    ResponseEntity<GenericResponse> response = booksController.addBook(book);
    System.out.println(response);
    assertEquals("Operation Performed Successfully.", response.getBody()
        .getMessage());
    assertEquals(HttpStatus.CREATED, response.getStatusCode());
    verify(booksService, times(1)).addBook(any(Books.class));
  }

  @Test
  public void fetchBook_shouldReturnBook_whenValidIdGiven() throws BooksException {

    when(booksService.getBook(anyLong())).thenReturn(book);
    ResponseEntity<GenericResponse> response = booksController.fetchBook(1L);
    assertEquals(book, response.getBody()
        .getData());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(booksService, times(1)).getBook(anyLong());
  }

  @Test
  public void addBook_shouldThrowException_whenNullBookGiven() throws BooksException {

    when(booksService.addBook(null)).thenThrow(BooksException.class);
    assertThrows(BooksException.class, () -> {
      booksController.addBook(null);
    });
  }

  @Test
  public void fetchAllBooks_shouldReturnAllBooks() throws BooksException {

    List<Books> booksList = new ArrayList<>();
    booksList.add(book);
    when(booksService.getAllBooks()).thenReturn(booksList);
    ResponseEntity<GenericResponse> response = booksController.fetchAllBooks();
    assertEquals(booksList, response.getBody()
        .getData());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(booksService, times(1)).getAllBooks();
  }

  @Test
  public void updateBook_shouldUpdateBook_whenValidIdAndBookGiven() throws BooksException {

    when(booksService.updateBook(anyLong(), any(Books.class))).thenReturn(book);
    ResponseEntity<GenericResponse> response = booksController.updateBook(1L, book);
    assertEquals(book, response.getBody()
        .getData());
    assertEquals(HttpStatus.ACCEPTED, response.getStatusCode());
    verify(booksService, times(1)).updateBook(anyLong(), any(Books.class));
  }

  @Test
  public void deleteBook_shouldDeleteBook_whenValidIdGiven() throws BooksException {

    when(booksService.deleteBook(anyLong())).thenReturn("Book deleted successfully.");
    ResponseEntity<GenericResponse> response = booksController.deleteBook(1L);
    assertEquals("Book deleted successfully.", response.getBody()
        .getMessage());
    assertEquals(HttpStatus.OK, response.getStatusCode());
    verify(booksService, times(1)).deleteBook(anyLong());
  }
}
