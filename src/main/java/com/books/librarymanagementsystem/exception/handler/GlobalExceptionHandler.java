package com.books.librarymanagementsystem.exception.handler;

import java.util.Set;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.NoHandlerFoundException;

import com.books.librarymanagementsystem.bo.GenericErrorResponse;
import com.books.librarymanagementsystem.exception.BooksException;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

@ControllerAdvice
public class GlobalExceptionHandler {

  @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
  public ResponseEntity<GenericErrorResponse> handleDefaultException(
      final HttpRequestMethodNotSupportedException ex) {

    return new ResponseEntity<>(
        new GenericErrorResponse(HttpStatus.METHOD_NOT_ALLOWED.value(), ex.getMessage()),
        HttpStatus.METHOD_NOT_ALLOWED);
  }

  @ExceptionHandler(AuthenticationException.class)
  public ResponseEntity<GenericErrorResponse> handleAuthenticationException(
      final AuthenticationException ex) {

    return new ResponseEntity<>(
        new GenericErrorResponse(HttpStatus.UNAUTHORIZED.value(), ex.getMessage()),
        HttpStatus.UNAUTHORIZED);
  }

  @ExceptionHandler(MethodArgumentNotValidException.class)
  public ResponseEntity<GenericErrorResponse> handleAuthenticationException(
      final MethodArgumentNotValidException ex) {

    StringBuilder message = new StringBuilder();
    ex.getBindingResult()
        .getAllErrors()
        .forEach((error) -> {
          String errorMessage = error.getDefaultMessage();
          message.append(errorMessage + " ");
        });
    return new ResponseEntity<>(
        new GenericErrorResponse(HttpStatus.BAD_REQUEST.value(), message.toString()),
        HttpStatus.BAD_REQUEST);
  }

  @ExceptionHandler(NoHandlerFoundException.class)
  @ResponseStatus(HttpStatus.NOT_FOUND)
  public ResponseEntity<GenericErrorResponse> handleDefaultException(
      final NoHandlerFoundException ex) {

    return new ResponseEntity<>(
        new GenericErrorResponse(HttpStatus.NOT_FOUND.value(), ex.getMessage()),
        HttpStatus.NOT_FOUND);
  }

  @ExceptionHandler(Exception.class)
  public ResponseEntity<GenericErrorResponse> handleCustomException(final Exception ex) {

    return new ResponseEntity<>(
        new GenericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getLocalizedMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(ConstraintViolationException.class)
  public ResponseEntity<GenericErrorResponse> handleConstraintViolationException(
      final ConstraintViolationException ex) {

    StringBuilder message = new StringBuilder();
    Set<ConstraintViolation<?>> violations = ex.getConstraintViolations();
    for (ConstraintViolation<?> violation : violations) {
      message.append(violation.getMessageTemplate()
          .concat(";"));
    }
    return new ResponseEntity<>(
        new GenericErrorResponse(HttpStatus.PRECONDITION_FAILED.value(),
            message.toString()),
        HttpStatus.PRECONDITION_FAILED);
  }

  @ExceptionHandler(BooksException.class)
  public ResponseEntity<GenericErrorResponse> handleBookNotFoundException(
      final BooksException ex) {

    return new ResponseEntity<>(
        new GenericErrorResponse(HttpStatus.INTERNAL_SERVER_ERROR.value(),
            ex.getMessage()),
        HttpStatus.INTERNAL_SERVER_ERROR);
  }

  @ExceptionHandler(value = AccessDeniedException.class)
  public ResponseEntity<GenericErrorResponse> handleAccessDeniedException(
      final AccessDeniedException ex) {

    return new ResponseEntity<>(
        new GenericErrorResponse(HttpStatus.FORBIDDEN.value(), ex.getMessage()),
        HttpStatus.FORBIDDEN);
  }
}
