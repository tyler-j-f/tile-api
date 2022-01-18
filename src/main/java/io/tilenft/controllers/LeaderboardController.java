package io.tilenft.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tilenft.eth.token.TokenLeaderboardRetriever;
import io.tilenft.sql.dtos.GetOverallRankDTO;
import io.tilenft.sql.dtos.LeaderboardEntryDTO;
import io.tilenft.sql.repositories.TokenRepository;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = {"/api/leaderboard"})
public class LeaderboardController extends BaseController {

  @Autowired private TokenLeaderboardRetriever tokenLeaderboardRetriever;
  @Autowired private TokenRepository tokenRepository;

  @GetMapping("getLeaders")
  public String getLeaders(
      @RequestParam(required = false, defaultValue = "0") int startIndex,
      @RequestParam(required = false, defaultValue = "5") int endIndex)
      throws JsonProcessingException {
    List<LeaderboardEntryDTO> leaderboardEntries = tokenLeaderboardRetriever.get(endIndex);
    return new ObjectMapper()
        .writeValueAsString(
            leaderboardEntries.subList(startIndex, getEndIndex(leaderboardEntries, endIndex)));
  }

  @GetMapping("getOverallRank/{tokenId}")
  public String getOverallRank(@PathVariable Long tokenId)
      throws JsonProcessingException, ControllerException {
    List<LeaderboardEntryDTO> leaderboardEntries =
        tokenLeaderboardRetriever.get(Math.toIntExact(tokenRepository.getCount()));
    LeaderboardEntryDTO foundEntry = null;
    for (LeaderboardEntryDTO entry : leaderboardEntries) {
      if (entry.getTokenId().equals(tokenId)) {
        foundEntry = entry;
        break;
      }
    }
    GetOverallRankDTO.GetOverallRankDTOBuilder getOverallRankDTOBuilder =
        GetOverallRankDTO.builder()
            .tokenId(tokenId)
            .totalTokenRanks(getHighestTokenRank(leaderboardEntries))
            .totalUnburntTokens((long) leaderboardEntries.size())
            .totalTokens(getNumberOfAllTokensLong());
    if (foundEntry == null) {
      getOverallRankDTOBuilder.rank(0L);
    } else {
      getOverallRankDTOBuilder.rank(foundEntry.getRankCount());
    }
    return new ObjectMapper().writeValueAsString(getOverallRankDTOBuilder.build());
  }

  private Long getHighestTokenRank(List<LeaderboardEntryDTO> leaderboardEntries) {
    Long highestRank = 0L;
    for (LeaderboardEntryDTO entry : leaderboardEntries) {
      Long rank = entry.getRankCount();
      if (rank > highestRank) {
        highestRank = rank;
      }
    }
    return highestRank;
  }

  private Long getNumberOfAllTokensLong() {
    return tokenRepository.getCount();
  }

  @GetMapping("getNumberOfAllTokens")
  public String getNumberOfAllTokens() throws JsonProcessingException {
    return new ObjectMapper().writeValueAsString(getNumberOfAllTokensLong());
  }

  @GetMapping("getNumberOfUnburntTokens")
  public String getNumberOfUnburntTokens() throws JsonProcessingException {
    return new ObjectMapper()
        .writeValueAsString(
            String.valueOf(tokenLeaderboardRetriever.getSize(tokenRepository.getCount())));
  }

  private int getEndIndex(List<LeaderboardEntryDTO> tokenIdList, int endIndex) {
    return Math.min(endIndex, tokenIdList.size());
  }
}
