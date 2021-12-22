package io.tilenft.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tilenft.eth.token.TokenLeaderboardRetriever;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/frontend"})
public class FrontendController extends BaseController {

  @Autowired private TokenLeaderboardRetriever tokenLeaderboardRetriever;

  @GetMapping("getLeaders")
  public String getLeaders(
      @RequestParam(required = false, defaultValue = "0") int startIndex,
      @RequestParam(required = false, defaultValue = "5") int endIndex)
      throws JsonProcessingException {
    return new ObjectMapper()
        .writeValueAsString(String.valueOf(tokenLeaderboardRetriever.get(startIndex, endIndex)));
  }
}
