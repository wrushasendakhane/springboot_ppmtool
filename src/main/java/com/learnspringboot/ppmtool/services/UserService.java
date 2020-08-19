package com.learnspringboot.ppmtool.services;

import com.learnspringboot.ppmtool.domain.User;
import com.learnspringboot.ppmtool.exceptions.UserAlreadyExistsException;
import com.learnspringboot.ppmtool.repositories.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

  @Autowired
  private UserRepository userRepository;

  @Autowired
  private BCryptPasswordEncoder bCryptPasswordEncoder;

  public User saveUser(User newUser) {
    try {
      newUser.setPassword(bCryptPasswordEncoder.encode(newUser.getPassword()));
      return userRepository.save(newUser);

    } catch (Exception e) {
      throw new UserAlreadyExistsException("User '" + newUser.getUsername() + "' already exists.");
    }
  }
}