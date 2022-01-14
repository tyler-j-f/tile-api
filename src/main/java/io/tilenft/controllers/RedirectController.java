package io.tilenft.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

@RestController
@RequestMapping(value = {"/"})
public class RedirectController extends BaseController {

  /**
   * If a request to load the site on another page is received, redirect to the homepage.
   *
   * @return RedirectView The frontend home page.
   */
  @GetMapping(value = {"leaderboard", "view", "contact", "update", "merge"})
  public RedirectView redirectToHomePage() {
    return new RedirectView("/");
  }
}
