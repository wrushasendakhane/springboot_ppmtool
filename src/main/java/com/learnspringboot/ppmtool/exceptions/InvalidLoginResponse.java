package com.learnspringboot.ppmtool.exceptions;

import lombok.Data;

@Data
public class InvalidLoginResponse {

  private String username;
  private String password;

  public InvalidLoginResponse() {
    this.username = "Invalid Username";
    this.password = "Invalid Password";
  }
}