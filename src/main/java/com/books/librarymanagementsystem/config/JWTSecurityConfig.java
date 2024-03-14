package com.books.librarymanagementsystem.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.books.librarymanagementsystem.filter.JwtAuthFilter;
import com.books.librarymanagementsystem.service.impl.UserInfoService;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
@EnableWebMvc
public class JWTSecurityConfig {

  private final JwtAuthFilter authFilter;

  public JWTSecurityConfig(JwtAuthFilter authFilter) {

    this.authFilter = authFilter;
  }

  /**
   * @author Vidhi_s Method for starting User Creation.
   * 
   * @return
   */
  @Bean
  public UserDetailsService userDetailsService() {

    return new UserInfoService();
  }

  @SuppressWarnings("removal")
  @Bean
  public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

    return http.csrf()
        .disable()
        .authorizeHttpRequests()
        .requestMatchers("/auth/welcome", "/auth/registerUser", "/auth/fetchAllUsers",
            "auth/generateToken", "/actuator/**", "/actuator/health", "/v3/api-docs",
            "/swagger-ui.html", "/api/swagger-ui.html", "/swagger-resources")
        .permitAll()
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/api/v1/auth/**", "/v3/api-docs/**", "/v3/api-docs.yaml",
            "/swagger-ui/**", "/swagger-ui.html")
        .permitAll()
        .and()
        .authorizeHttpRequests()
        .requestMatchers("/auth/user/**", "/auth/admin/**", "/books/**")
        .authenticated()
        .and()
        .sessionManagement()
        .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
        .and()
        .authenticationProvider(authenticationProvider())
        .addFilterBefore(authFilter, UsernamePasswordAuthenticationFilter.class)
        .build();
  }

  /**
   * @author Vidhi_s Method for encoding password using BcryptEncoder.
   * 
   * @return
   */
  @Bean
  public PasswordEncoder passwordEncoder() {

    return new BCryptPasswordEncoder();
  }

  @Bean
  public AuthenticationProvider authenticationProvider() {

    DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
    authenticationProvider.setUserDetailsService(userDetailsService());
    authenticationProvider.setPasswordEncoder(passwordEncoder());
    return authenticationProvider;
  }

  @Bean
  public AuthenticationManager authenticationManager(AuthenticationConfiguration config)
      throws Exception {

    return config.getAuthenticationManager();
  }
}
