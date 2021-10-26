package com.tylerfitzgerald.demo_api.controller;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
public class BaseController {
  @ExceptionHandler(Exception.class)
  public String handleException(HttpServletRequest request, Exception e) {
    return e.toString();
  }
}
