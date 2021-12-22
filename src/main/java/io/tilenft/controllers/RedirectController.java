package io.tilenft.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = {"/"})
public class RedirectController extends BaseController {

  /**
   * Redirect to the homepage, if a request to load the site on another page is received.
   *
   * @return RedirectView The frontend home page.
   */
  @GetMapping(value = {"leaderboard", "view", "contact"})
  public RedirectView redirectToHomePage() {
    return new RedirectView("/");
  }
}
