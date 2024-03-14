package com.books.librarymanagementsystem.bo;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class GenericErrorResponse {

  private Integer code;

  private String message;
}
