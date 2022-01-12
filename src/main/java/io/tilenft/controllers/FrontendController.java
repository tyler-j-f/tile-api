package io.tilenft.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tilenft.eth.token.TokenLeaderboardRetriever;
import io.tilenft.sql.repositories.TokenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/frontend"})
public class FrontendController extends BaseController {

  @Autowired private TokenLeaderboardRetriever tokenLeaderboardRetriever;
  @Autowired private TokenRepository tokenRepository;

  @GetMapping("getLeaders")
  public String getLeaders(
      @RequestParam(required = false, defaultValue = "0") int startIndex,
      @RequestParam(required = false, defaultValue = "5") int endIndex)
      throws JsonProcessingException {
    List<Long> tokenIdList = tokenLeaderboardRetriever.get(endIndex);
    return new ObjectMapper()
        .writeValueAsString(
            String.valueOf(tokenIdList.subList(startIndex, getEndIndex(tokenIdList, endIndex))));
  }

  private int getEndIndex(List<Long> tokenIdList, int endIndex) {
    return Math.min(endIndex, tokenIdList.size());
  }

  @GetMapping("getNumberOfTokens")
  public String getNumberOfTokens() throws JsonProcessingException {
    return new ObjectMapper()
        .writeValueAsString(
            String.valueOf(tokenLeaderboardRetriever.getSize(tokenRepository.getCount())));
  }
}
