package io.tilenft.eth.token;

import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeConstants;
import io.tilenft.eth.token.traits.weightless.WeightlessTraitTypeConstants;
import io.tilenft.sql.daos.TokenLeaderboardDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenLeaderboardRetriever {
  @Autowired private TokenLeaderboardDao tokenLeaderboardDao;
  private static final int START_INDEX_DEFAULT = 0;
  private static final int DEFAULT_PAGE_SIZE = 5;

  public List<Long> get() {
    return get(START_INDEX_DEFAULT, START_INDEX_DEFAULT + DEFAULT_PAGE_SIZE);
  }

  public List<Long> get(int startIndex) {
    return get(startIndex, startIndex + DEFAULT_PAGE_SIZE);
  }

  public List<Long> get(int startIndex, int endIndex) {
    List<Long> leaderTokenIds =
        tokenLeaderboardDao.getLeaderTokenIds(
            (long) WeightlessTraitTypeConstants.OVERALL_RARITY,
            (long) WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE,
            endIndex - startIndex,
            startIndex);
    return leaderTokenIds;
  }

  public Long getSize(Long totalNumberOfTokens) {
    List<Long> leaderTokenIds =
        tokenLeaderboardDao.getLeaderTokenIds(
            (long) WeightlessTraitTypeConstants.OVERALL_RARITY,
            (long) WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE,
            Math.toIntExact(totalNumberOfTokens),
            0);
    return (long) leaderTokenIds.size();
  }
}
