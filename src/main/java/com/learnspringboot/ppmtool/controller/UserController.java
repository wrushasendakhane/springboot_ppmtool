package com.learnspringboot.ppmtool.controller;

import static com.learnspringboot.ppmtool.security.SecurityConstants.TOKEN_PREFIX;

import javax.validation.Valid;

import com.learnspringboot.ppmtool.domain.User;
import com.learnspringboot.ppmtool.payload.JWTLoginSuccessResponse;
import com.learnspringboot.ppmtool.payload.LoginRequest;
import com.learnspringboot.ppmtool.security.JwtTokenProvider;
import com.learnspringboot.ppmtool.services.MapValidationErrorService;
import com.learnspringboot.ppmtool.services.UserService;
import com.learnspringboot.ppmtool.validator.UserValidator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/api/users")
@CrossOrigin
public class UserController {

  @Autowired
  private MapValidationErrorService validationService;

  @Autowired
  private UserService userService;

  @Autowired
  private UserValidator userValidator;

  @Autowired
  private JwtTokenProvider jwtTokenProvider;

  @Autowired
  private AuthenticationManager authenticationManager;

  @PostMapping("/login")
  public ResponseEntity<?> authenticateUser(@Valid @RequestBody LoginRequest loginRequest, BindingResult result) {
    ResponseEntity<?> errorMap = validationService.MapValidationService(result);
    if (errorMap != null) {
      return errorMap;
    }
    Authentication authentication = authenticationManager
        .authenticate(new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword()));

    SecurityContextHolder.getContext().setAuthentication(authentication);
    String jwt = TOKEN_PREFIX + jwtTokenProvider.generateToken(authentication);

    return ResponseEntity.ok(new JWTLoginSuccessResponse(true, jwt));
  }

  @PostMapping(path = "/register")
  public ResponseEntity<?> registerUser(@Valid @RequestBody User user, BindingResult result) {
    userValidator.validate(user, result);
    ResponseEntity<?> errorMap = validationService.MapValidationService(result);
    if (errorMap != null)
      return errorMap;
    return new ResponseEntity<User>(userService.saveUser(user), HttpStatus.CREATED);
  }
}