package com.books.librarymanagementsystem.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.books.librarymanagementsystem.entity.UserInfo;
import com.books.librarymanagementsystem.entity.UserInfoDetails;
import com.books.librarymanagementsystem.repo.UserInfoRepository;
import com.books.librarymanagementsystem.util.LibraryUtil;

import lombok.extern.slf4j.Slf4j;

/**
 * @author Vidhi_s
 */
@Service
@Slf4j
public class UserInfoService implements UserDetailsService {

  @Autowired
  private UserInfoRepository repository;

  @Autowired
  private PasswordEncoder passowrdEncoder;

  @Override
  public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

    log.info(LibraryUtil.logFormat("Finding the user by username: {} from data.",
        new String[] {username}));
    Optional<UserInfo> userDetail = repository.findByUserName(username);
    /** Converting userDetail to UserDetails. */
    return userDetail.map(UserInfoDetails::new)
        .orElseThrow(() -> new UsernameNotFoundException("User not found: " + username));
  }

  /**
   * @author Vidhi_s to add new User.
   * 
   * @param userInfo
   * @return
   */
  public UserInfo addUser(UserInfo userInfo) {

    log.info(LibraryUtil.logFormat("Adding the user: {} to database.",
        new String[] {userInfo.getUserName()}));
    userInfo.setPassword(passowrdEncoder.encode(userInfo.getPassword()));
    UserInfo user = repository.save(userInfo);
    return user;
  }

  /**
   * @author Vidhi_s To fetch all the users from data.
   * 
   * @return
   */
  public List<UserInfo> fetchUsers() {

    log.info(LibraryUtil.logFormat("Fetching all the users from database.", null));
    return repository.findAll();
  }
}
