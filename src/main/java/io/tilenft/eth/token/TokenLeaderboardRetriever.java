package io.tilenft.eth.token;

import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.repositories.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenLeaderboardRetriever {
  @Autowired private WeightlessTraitRepository weightlessTraitRepository;
  private static final Long OVERALL_RARITY_TRAIT_TYPE_ID = 23L;
  private static final int START_INDEX_DEFAULT = 0;
  private static final int DEFAULT_PAGE_SIZE = 5;

  public List<Long> get() {
    return get(START_INDEX_DEFAULT, START_INDEX_DEFAULT + DEFAULT_PAGE_SIZE);
  }

  public List<Long> get(int startIndex) {
    return get(startIndex, startIndex + DEFAULT_PAGE_SIZE);
  }

  public List<Long> get(int startIndex, int endIndex) {
    List<Long> tokenIds = new ArrayList<>();
    int x = 0;
    for (WeightlessTraitDTO trait :
        weightlessTraitRepository.readByTraitTypeId(
            OVERALL_RARITY_TRAIT_TYPE_ID, endIndex - startIndex)) {
      x++;
      if ((x - 1) >= endIndex) break;
      if ((x - 1) < startIndex) {
        x++;
        continue;
      }
      tokenIds.add(trait.getTokenId());
    }
    return tokenIds;
  }
}
