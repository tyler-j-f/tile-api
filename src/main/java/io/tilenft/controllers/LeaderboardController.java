package io.tilenft.controllers;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.tilenft.eth.token.TokenFacade;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.TokenLeaderboardRetriever;
import io.tilenft.eth.token.TokenRetriever;
import io.tilenft.eth.token.initializers.MergeTokenHandler;
import io.tilenft.eth.token.initializers.TokenInitializeException;
import io.tilenft.sql.dtos.GetOverallRankDTO;
import io.tilenft.sql.dtos.LeaderboardEntryDTO;
import io.tilenft.sql.dtos.TotalTokensDTO;
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
  @Autowired private TokenRetriever tokenRetriever;
  @Autowired private MergeTokenHandler mergeTokenHandler;
  @Autowired private TokenFacade tokenFacade;

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
    Long numberOfTokens = getNumberOfAllTokensLong();
    List<LeaderboardEntryDTO> leaderboardEntries =
        tokenLeaderboardRetriever.get(Math.toIntExact(numberOfTokens));
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
            .totalTokens(numberOfTokens);
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

  @GetMapping("getTotalTokensData")
  public String getTotalTokensData() throws JsonProcessingException {
    Long numberOfTokens = getNumberOfAllTokensLong();
    return new ObjectMapper()
        .writeValueAsString(
            TotalTokensDTO.builder()
                .totalTokens(getNumberOfAllTokensLong())
                .totalUnburntTokens(
                    (long) tokenLeaderboardRetriever.get(Math.toIntExact(numberOfTokens)).size())
                .build());
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

  @GetMapping("getMergeOverallRarity/{burnedToken1}/{burnedToken2}")
  public String getMergeOverallRarity(
      @PathVariable Long burnedToken1, @PathVariable Long burnedToken2)
      throws JsonProcessingException, TokenInitializeException, ControllerException {
    TokenFacadeDTO burnedNft1 = tokenRetriever.get(burnedToken1);
    TokenFacadeDTO burnedNft2 = tokenRetriever.get(burnedToken2);
    if (burnedNft1 == null || burnedNft2 == null) {
      throw new ControllerException("burnedNft1 or burnedNft2 is null");
    }
    TokenFacadeDTO mergeTokenWouldBeResult =
        mergeTokenHandler.mintNewTokenForMerge(
            getNumberOfAllTokensLong() + 1,
            burnedNft1,
            burnedNft2,
            System.currentTimeMillis(),
            true);
    return new ObjectMapper()
        .writeValueAsString(tokenFacade.loadToken(mergeTokenWouldBeResult).buildTokenMetadataDTO());
  }

  private int getEndIndex(List<LeaderboardEntryDTO> tokenIdList, int endIndex) {
    return Math.min(endIndex, tokenIdList.size());
  }
}
