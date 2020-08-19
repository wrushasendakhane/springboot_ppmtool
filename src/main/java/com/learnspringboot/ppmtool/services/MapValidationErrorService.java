package com.learnspringboot.ppmtool.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

@Service
public class MapValidationErrorService {

  public ResponseEntity<?> MapValidationService(BindingResult result) {
    if (result.hasErrors()) {
      Map<String, String> errorMap = new HashMap<>();
      List<FieldError> errors = result.getFieldErrors();
      for (FieldError error : errors) {
        errorMap.put(error.getField(), error.getDefaultMessage());
      }
      return ResponseEntity.badRequest().body(errorMap);
    }
    return null;
  }
}