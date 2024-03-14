package com.books.librarymanagementsystem.service;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;

@Component
public class JwtService {

  public static final String SECRET =
      "5367566B59703373367639792F423F4528482B4D6251655468576D5A71347437";

  /**
   * @param userName
   * @return
   */
  public String generateToken(String userName) {

    Map<String, Object> claims = new HashMap<>();
    return createToken(claims, userName);
  }

  /**
   * @param claims
   * @param userName
   * @return
   */
  private String createToken(Map<String, Object> claims, String userName) {

    return Jwts.builder()
        .setClaims(claims)
        .setSubject(userName)
        .setIssuedAt(new Date(System.currentTimeMillis()))
        .setExpiration(new Date(System.currentTimeMillis() + 1000 * 60 * 30))
        .signWith(getSignKey(), SignatureAlgorithm.HS256)
        .compact();
  }

  /**
   * @return
   */
  private Key getSignKey() {

    byte[] keyBytes = Decoders.BASE64.decode(SECRET);
    return Keys.hmacShaKeyFor(keyBytes);
  }

  /**
   * @param token
   * @return
   */
  public String extractUsername(String token) {

    return extractClaim(token, Claims::getSubject);
  }

  /**
   * @param token
   * @return
   */
  public Date extractExpiration(String token) {

    return extractClaim(token, Claims::getExpiration);
  }

  /**
   * @param <T>
   * @param token
   * @param claimsResolver
   * @return
   */
  public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {

    final Claims claims = extractAllClaims(token);
    return claimsResolver.apply(claims);
  }

  /**
   * @param token
   * @return
   */
  private Claims extractAllClaims(String token) {

    return Jwts.parserBuilder()
        .setSigningKey(getSignKey())
        .build()
        .parseClaimsJws(token)
        .getBody();
  }

  /**
   * @param token
   * @return
   */
  private Boolean isTokenExpired(String token) {

    return extractExpiration(token).before(new Date());
  }

  /**
   * @param token
   * @param userDetails
   * @return
   */
  public Boolean validateToken(String token, UserDetails userDetails) {

    final String username = extractUsername(token);
    return (username.equals(userDetails.getUsername()) && !isTokenExpired(token));
  }
}
