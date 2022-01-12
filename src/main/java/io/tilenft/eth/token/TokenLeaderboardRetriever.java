package io.tilenft.eth.token;

import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeConstants;
import io.tilenft.eth.token.traits.weightless.WeightlessTraitTypeConstants;
import io.tilenft.sql.daos.TokenLeaderboardDao;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenLeaderboardRetriever {
  @Autowired private TokenLeaderboardDao tokenLeaderboardDao;

  public List<Long> get(int totalNumberOfTokens) {
    List<Long> leaderTokenIds =
        tokenLeaderboardDao.getLeaderTokenIds(
            (long) WeightlessTraitTypeConstants.OVERALL_RARITY,
            (long) WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE,
            totalNumberOfTokens,
            0);
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
