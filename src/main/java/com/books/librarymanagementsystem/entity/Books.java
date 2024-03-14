package com.books.librarymanagementsystem.entity;

import java.time.Year;

import com.books.librarymanagementsystem.bo.Auditable;

import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "books")
public class Books extends Auditable {

  @NotNull(message = "Book title must not be null.")
  private String title;

  @NotNull(message = "Book Author must not be null.")
  private String author;

  @NotNull(message = "Publication year must not be null.")
  private Year publicationYear;
}
