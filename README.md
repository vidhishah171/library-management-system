# Introduction
A Library Management System designed to see the books available in a college library. It allows librarians/students to register as a user and add/delete and view books from the college library hassle free. The backend is designed as a Monolithic Architecture with various nuances as discussed below.

# Technologies and Dependencies Used
- Maven used as a dependency management tool.
- Java version: 17 and Spring Boot version: 3.1.9 are used to build hassle free web applications and writing REST APIs.
- Spring Security used for Authentication and Authorizations using JWT Tokens.
- Spring data JPA (Hibernate) Used to reduce the time of writing hardcoded sql queries and instead allows to write much more readable and scalable code.
- H2 in memory database is used as a Java persistence store.
- Project Lombok Reduces the time of writing java boiler plate code.
- Swagger is used to for the API documentation.
- JUnit 4 and Mockito are used for writing test case scenarios.
- JPA Auditing for storing Auditable data.

# Using Library Management System
CLI-->

  git clone https://github.com/vidhishah171/library-management-system.git
  cd library-management-system
  mvn package 
  java -jar target/library-management-system-0.0.1-SNAPSHOT.jar
  Intellij/Eclipse-->

1. Let maven resolve dependencies.
2. Run SpringBootApplication.

# Backend Design
Actors/Entities are inspired by the real world entities that can use the applications.
1. **Book** having attributes:
- Unique primary key id, title, author, publicationYear.
2. **User** used mainly for authentication and authorization has attributes:
unique primary key id, roles(ROLE_ADMIN, ROLE_USER), userName, password, email.

# Functionalities Exposed
1. User Controller Class:
The REST APIs exposed are
- CRUD APIs for the register, welcome, get Users. The register user API http://localhost:8080/auth/registerUser creates a user entity along with the given roles for that user. User password is encoded using Bcrypt Encoder for security.
- Another API which is exposed is to generate JWT Token for the registerd user. That is http://localhost:8080/auth/generateToken with parameters are username and password.
It returns the JWT token for the registered user, which is valid for 30 minutes by default.

2. Books Controller Class:
- For the books entity we have REST APIs for Basic CRUD operations such as addBook, updateBook, getBook, deleteBook using the respected parameters and adding jwt token in the header part.
- Also, user can add Book by adding title, author and publicationYear in the Query parameters.
- Only "ROLE_ADMIN" authority users can add/update/delete the books stored in the database.
- "RoLE_USER" authority users can view the books by id and fetch all the books from the database.

# Security (Checkout Branch Security)
Spring Security is used for Authentication and Authorization. For every API call it is checked whether the calling entity has JWT Bearer token that make it a valid entity in the system.

Few Examples: Each example API preceeded by "http://localhost:8080"

/auth/welcome--> No security endpoint

/auth/registerUser --> To register user in the system

/auth/fetchAllUsers --> To fetch all users from the system

/auth/user/userProfile --> To check for User role. (ROLE_USER)

/auth/admin/adminProfile --> To check for Admin role. (ROLE_ADMIN)

/auth/generateToken--> To generate and get the JWT token for registered user which is needed to use for BooksController APIs. (ROLE_USER)

/books/add --> To add book in the system. (ROLE_ADMIN)

/books/add/by-parameters?title={title}&author={author}&publicationYear={year} --> To add book in the system using parameters. (ROLE_ADMIN)

/books/update/{id} --> To update book by particular Id. (ROLE_ADMIN)

/books/delete/{id} --> To delete book by particular Id (ROLE_ADMIN)

/books/get/{id} --> To get book by particular Id. (ROLE_ADMIN, ROLE_USER)

/books/get --> To get all the books from system. (ROLE_ADMIN, ROLE_USER)

# Exception Handling

For the Library Management System Custom Books Exceptions are created and all the Exceptions and Http response codes are handled in the GlobalExceptionHandler class such as:

- Internal Server Exceptions
- Authentication Exception
- NoHandlerFoundException, MethodNotAllowed Exception
- MethodArgumentNotValidException etc.

# Author and Developed by
**Vidhi Shah**
