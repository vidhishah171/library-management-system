package com.books.librarymanagementsystem.bo;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthRequest {

  @NotBlank(message = "Username must not be blank.")
  private String userName;

  @NotBlank(message = "Password must not be blank.")
  private String password;
}
