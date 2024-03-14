package com.books.librarymanagementsystem.rest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.books.librarymanagementsystem.bo.AuthRequest;
import com.books.librarymanagementsystem.bo.GenericResponse;
import com.books.librarymanagementsystem.entity.UserInfo;
import com.books.librarymanagementsystem.service.JwtService;
import com.books.librarymanagementsystem.service.impl.UserInfoService;
import com.books.librarymanagementsystem.util.LibraryUtil;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;

@RestController
@Tag(name = "Auth-API", description = "User management APIs")
@RequestMapping("/auth")
public class UserController {

  @Autowired
  private UserInfoService userInfoService;

  @Autowired
  private JwtService jwtService;

  @Autowired
  private AuthenticationManager authenticationManager;

  @ApiResponses({@ApiResponse(responseCode = "201",
      content = @Content(schema = @Schema(implementation = String.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "Welcome Endpoint.")
  @GetMapping("/welcome")
  public ResponseEntity<GenericResponse> welcome() {

    return LibraryUtil.buildSuccessResponse("Welcome! This endpoint is not secure.", HttpStatus.OK);
  }

  @ApiResponses({@ApiResponse(responseCode = "201",
      content = @Content(schema = @Schema(implementation = String.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "To register the user to library management system.")
  @PostMapping("/registerUser")
  @ResponseStatus(HttpStatus.CREATED)
  public ResponseEntity<GenericResponse> addNewUser(@Parameter(
      description = "User Object to register", schema = @Schema(implementation = UserInfo.class),
      required = true) @Valid @RequestBody UserInfo userInfo) {

    return LibraryUtil.buildSuccessResponse(userInfoService.addUser(userInfo), HttpStatus.CREATED);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
      content = @Content(schema = @Schema(implementation = List.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "To fetch all the users to library management system.")
  @GetMapping("/fetchAllUsers")
  public ResponseEntity<GenericResponse> fetchAllUsers() {

    return LibraryUtil.buildSuccessResponse(userInfoService.fetchUsers(), HttpStatus.OK);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
      content = @Content(schema = @Schema(implementation = String.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "To check the user role authority.")
  @GetMapping("/user/userProfile")
  @PreAuthorize("hasAuthority('ROLE_USER')")
  public ResponseEntity<GenericResponse> userProfile() {

    return LibraryUtil.buildSuccessResponse("Welcome to User Profile", HttpStatus.OK);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
      content = @Content(schema = @Schema(implementation = String.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "To check the admin role authority.")
  @GetMapping("/admin/adminProfile")
  @PreAuthorize("hasAuthority('ROLE_ADMIN')")
  public ResponseEntity<GenericResponse> adminProfile() {

    return LibraryUtil.buildSuccessResponse("Welcome to Admin Profile", HttpStatus.OK);
  }

  @ApiResponses({@ApiResponse(responseCode = "200",
      content = @Content(schema = @Schema(implementation = String.class),
          mediaType = MediaType.APPLICATION_JSON_VALUE))})
  @Operation(description = "To generate token for the registered user.")
  @PostMapping("/generateToken")
  public ResponseEntity<GenericResponse> authenticateAndGetToken(@Parameter(
      description = "Auth Request Object", schema = @Schema(implementation = AuthRequest.class),
      required = true) @RequestBody @Valid AuthRequest authRequest) {

    Authentication authentication =
        authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
            authRequest.getUserName(), authRequest.getPassword()));
    if (authentication.isAuthenticated()) {
      return LibraryUtil.buildSuccessResponse("",
          jwtService.generateToken(authRequest.getUserName()));
    } else {
      throw new UsernameNotFoundException("Invalid User request!");
    }
  }
}
