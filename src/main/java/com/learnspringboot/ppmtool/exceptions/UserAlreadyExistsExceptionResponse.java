package com.learnspringboot.ppmtool.exceptions;

import lombok.Data;

@Data
public class UserAlreadyExistsExceptionResponse {

  private String username;

  public UserAlreadyExistsExceptionResponse(String username) {
    this.username = username;
  }
}