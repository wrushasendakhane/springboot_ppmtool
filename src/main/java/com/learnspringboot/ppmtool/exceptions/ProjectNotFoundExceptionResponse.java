package com.learnspringboot.ppmtool.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import lombok.Data;

@Data
public class ProjectNotFoundExceptionResponse {

  private String ProjectNotFound;

  public ProjectNotFoundExceptionResponse(String ProjectNotFound) {
    this.ProjectNotFound = ProjectNotFound;
  }

}