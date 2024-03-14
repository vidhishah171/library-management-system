package com.books.librarymanagementsystem.filter;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.servlet.HandlerExceptionResolver;

import com.books.librarymanagementsystem.bo.GenericErrorResponse;
import com.books.librarymanagementsystem.service.JwtService;
import com.books.librarymanagementsystem.service.impl.UserInfoService;
import com.books.librarymanagementsystem.util.LibraryUtil;

import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * @author Vidhi_s This class helps us to validate the generated jwt token.
 * 
 */
@Component
public class JwtAuthFilter extends OncePerRequestFilter {

  @Autowired
  private JwtService jwtService;

  @Autowired
  private UserInfoService userDetailsService;

  @Autowired
  @Qualifier("handlerExceptionResolver")
  private HandlerExceptionResolver resolver;

  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
      FilterChain filterChain) throws ServletException, IOException, JwtException {

    String authHeader = request.getHeader("Authorization");
    String token = null;
    String username = null;
    try {
      if (authHeader != null && authHeader.startsWith("Bearer ")) {
        token = authHeader.substring(7);
        username = jwtService.extractUsername(token);
      }
      if (username != null && SecurityContextHolder.getContext()
          .getAuthentication() == null) {
        UserDetails userDetails = userDetailsService.loadUserByUsername(username);
        if (jwtService.validateToken(token, userDetails)) {
          UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
              userDetails, null, userDetails.getAuthorities());
          authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
          SecurityContextHolder.getContext()
              .setAuthentication(authToken);
        }
      }
      filterChain.doFilter(request, response);
    } catch (Exception e) {
      GenericErrorResponse errorResponse =
          new GenericErrorResponse(HttpStatus.UNAUTHORIZED.value(), e.getMessage());
      response.setStatus(HttpStatus.UNAUTHORIZED.value());
      response.getWriter()
          .write(LibraryUtil.convertObjectToJson(errorResponse));
    }
  }
}
