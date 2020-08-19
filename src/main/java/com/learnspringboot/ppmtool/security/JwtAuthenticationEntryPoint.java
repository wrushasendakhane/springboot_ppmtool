package com.learnspringboot.ppmtool.security;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.gson.Gson;
import com.learnspringboot.ppmtool.exceptions.InvalidLoginResponse;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {
  @Override
  public void commence(HttpServletRequest req, HttpServletResponse res, AuthenticationException arg2)
      throws IOException, ServletException {

    InvalidLoginResponse loginResponse = new InvalidLoginResponse();
    String responseString = new Gson().toJson(loginResponse);
    res.setContentType("application/json");
    res.setStatus(401);
    res.getWriter().print(responseString);
  }
}