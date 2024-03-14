package com.books.librarymanagementsystem;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;
import org.springframework.boot.test.context.SpringBootTest;

import com.books.librarymanagementsystem.rest.BooksControllerTest;
import com.books.librarymanagementsystem.service.BooksServiceTest;

@SpringBootTest
@RunWith(Suite.class)
@SuiteClasses({BooksServiceTest.class, BooksControllerTest.class})
class LibraryManagementSystemApplicationTests {

  @Test
  void contextLoads() {}
}
