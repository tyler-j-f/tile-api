package io.tilenft.controllers;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = {"/error"})
public class AppErrorController implements ErrorController {

  @RequestMapping("")
  public RedirectView handleError(HttpServletRequest request) {
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    if (status != null) {
      Integer statusCode = Integer.valueOf(status.toString());
      int statusCode500 = HttpStatus.INTERNAL_SERVER_ERROR.value();
      int statusCode404 = HttpStatus.NOT_FOUND.value();
      if (statusCode == statusCode404) {
        return new RedirectView("/?errorCode=404");
      } else if (statusCode == statusCode500) {
        return new RedirectView("/?errorCode=500");
      }
    }
    return new RedirectView("/");
  }
}
