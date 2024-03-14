package com.books.librarymanagementsystem.rest;

import java.time.Year;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.books.librarymanagementsystem.bo.GenericResponse;
import com.books.librarymanagementsystem.entity.Books;
import com.books.librarymanagementsystem.exception.BooksException;
import com.books.librarymanagementsystem.service.BooksService;
import com.books.librarymanagementsystem.util.LibraryUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;

@RestController
@Tag(name = "Books-API", description = "Book management APIs")
@RequestMapping("/books")
public class BooksController {

  @Autowired
  private BooksService booksService;

  @ApiResponses({@ApiResponse(responseCode = "201",
      content = @Content(schema = @Schema(implementation = String.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "Add book object to the library with required parameters.")
  @PostMapping(value = "/add", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<GenericResponse> addBook(@Parameter(description = "Book Object to save",
      schema = @Schema(implementation = Books.class),
      required = true) @RequestBody @Valid Books bookObject) throws BooksException {

    return LibraryUtil.buildSuccessResponse(this.booksService.addBook(bookObject),
        HttpStatus.CREATED);
  }

  @ApiResponses({@ApiResponse(responseCode = "201",
      content = @Content(schema = @Schema(implementation = String.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "Add book object to the library with by parameters.")
  @PostMapping(value = "/add/by-parameters", produces = MediaType.APPLICATION_JSON_VALUE)
  @ResponseStatus(HttpStatus.CREATED)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<GenericResponse> addBookByParameters(
      @Parameter(description = "Title of the book", schema = @Schema(implementation = Books.class),
          required = true) @NotNull @RequestParam String title,
      @Parameter(description = "Author of the book", schema = @Schema(implementation = Books.class),
          required = true) @NotNull @RequestParam String author,
      @Parameter(description = "Publication year of the book",
          schema = @Schema(implementation = Books.class),
          required = true) @NotNull @RequestParam Year publicationYear)
      throws BooksException {

    return LibraryUtil.buildSuccessResponse(
        this.booksService.addBook(author, title, publicationYear), HttpStatus.CREATED);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
      content = @Content(schema = @Schema(implementation = Books.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "Fetch book object by id from the library.")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/get/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GenericResponse> fetchBook(@Parameter(description = "Id of book object",
      schema = @Schema(implementation = Long.class), required = true) @PathVariable Long id)
      throws BooksException {

    return LibraryUtil.buildSuccessResponse(this.booksService.getBook(id), HttpStatus.OK);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
      content = @Content(schema = @Schema(implementation = List.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "Fetch all the books from the library.")
  @ResponseStatus(HttpStatus.OK)
  @GetMapping(value = "/get", produces = MediaType.APPLICATION_JSON_VALUE)
  public ResponseEntity<GenericResponse> fetchAllBooks() throws BooksException {

    return LibraryUtil.buildSuccessResponse(this.booksService.getAllBooks(), HttpStatus.OK);
  }

  @ApiResponses({@ApiResponse(responseCode = "202",
      content = @Content(schema = @Schema(implementation = Books.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "Update the book object by id from the library.")
  @ResponseStatus(HttpStatus.ACCEPTED)
  @PutMapping(value = "/update/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<GenericResponse> updateBook(
      @Parameter(description = "Id of book object", schema = @Schema(implementation = Long.class),
          required = true) @PathVariable Long id,
      @Parameter(description = "Book Object to update",
          schema = @Schema(implementation = Books.class),
          required = true) @RequestBody @Valid Books bookObject)
      throws BooksException {

    return LibraryUtil.buildSuccessResponse(this.booksService.updateBook(id, bookObject),
        HttpStatus.ACCEPTED);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
      content = @Content(schema = @Schema(implementation = String.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "Delete book object by id from the library.")
  @ResponseStatus(HttpStatus.OK)
  @DeleteMapping(value = "delete/{id}", produces = MediaType.APPLICATION_JSON_VALUE)
  @PreAuthorize("hasRole('ROLE_ADMIN')")
  public ResponseEntity<GenericResponse> deleteBook(@Parameter(description = "Id of book object",
      schema = @Schema(implementation = Long.class), required = true) @PathVariable Long id)
      throws BooksException {

    return LibraryUtil.buildSuccessResponse(this.booksService.deleteBook(id), HttpStatus.OK);
  }
}
