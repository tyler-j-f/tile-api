package io.tileNft.controller;

import javax.servlet.http.HttpServletRequest;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;

@RestController
public abstract class BaseController {
  @ExceptionHandler(Exception.class)
  public String handleException(HttpServletRequest request, Exception e) {
    e.printStackTrace();
    return e.toString() + "\nStack Trace: " + getFormattedStackTrace(e);
  }

  private String getFormattedStackTrace(Exception e) {
    String out = "";
    for (StackTraceElement stackTraceElement : e.getStackTrace()) {
      out = out + "\n" + stackTraceElement.toString();
    }
    return out;
  }
}
