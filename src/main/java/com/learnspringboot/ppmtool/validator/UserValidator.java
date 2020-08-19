package com.learnspringboot.ppmtool.validator;

import java.util.Set;

import javax.validation.ConstraintViolation;

import com.learnspringboot.ppmtool.domain.User;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserValidator implements Validator {
  @Override
  public boolean supports(Class<?> clazz) {
    return User.class.equals(clazz);
  }

  @Override
  public void validate(Object target, Errors errors) {
    User user = (User) target;

    if (user.getPassword().length() < 6) {
      errors.rejectValue("password", "Length", "Password should be atleast 6 chars long");
    }
    if (!user.getPassword().equals(user.getConfirmPassword())) {
      errors.rejectValue("confirmPassword", "Match", "Password & Confirm password both should match");
    }
  }
}