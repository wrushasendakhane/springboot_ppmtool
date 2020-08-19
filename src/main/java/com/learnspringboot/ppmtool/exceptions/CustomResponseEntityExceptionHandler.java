package com.learnspringboot.ppmtool.exceptions;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
@RestController
public class CustomResponseEntityExceptionHandler extends ResponseEntityExceptionHandler {

  @ExceptionHandler
  public ResponseEntity<Object> handleProjectIdException(ProjectIdException ex, WebRequest request) {
    ProjectIdExceptionResponse response = new ProjectIdExceptionResponse(ex.getMessage());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleProjectNotFoundException(ProjectNotFoundException ex, WebRequest request) {
    ProjectNotFoundExceptionResponse response = new ProjectNotFoundExceptionResponse(ex.getMessage());
    return ResponseEntity.badRequest().body(response);
  }

  @ExceptionHandler
  public ResponseEntity<Object> handleUserAlreadyExistsException(UserAlreadyExistsException ex, WebRequest request) {
    UserAlreadyExistsExceptionResponse response = new UserAlreadyExistsExceptionResponse(ex.getMessage());
    return ResponseEntity.badRequest().body(response);
  }

}