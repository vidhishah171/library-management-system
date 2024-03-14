package com.books.librarymanagementsystem.bo;

import lombok.Data;

@Data
public class GenericResponse {

  private int status;

  private String message;

  private Object data;

  public GenericResponse() {}

  /**
   * @param status
   * @param message
   */
  public GenericResponse(int status, String message) {

    super();
    this.status = status;
    this.message = message;
  }

  public GenericResponse(int status, String message, Object data) {

    super();
    this.status = status;
    this.message = message;
    this.data = data;
  }
}
