package com.books.librarymanagementsystem.entity;

import java.util.List;

import com.books.librarymanagementsystem.bo.Auditable;

import jakarta.persistence.Entity;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserInfo extends Auditable {

  @NotBlank(message = "Username must not empty.")
  private String userName;

  @NotBlank(message = "User email must not empty.")
  @Email(regexp = "^\\w+(?:[.-]\\w+)*@\\w+(?:[.-]\\w+)*(?:\\.\\w{2,20})+$")
  private String email;

  @NotBlank(message = "Password must not empty.")
  private String password;

  @NotEmpty(message = "User should have atleast 1 role.")
  private List<String> roles;
}
