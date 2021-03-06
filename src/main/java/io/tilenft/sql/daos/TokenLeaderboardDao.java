package io.tilenft.sql.daos;

import io.tilenft.sql.dtos.GetLeaderEntriesDTO;
import io.tilenft.sql.dtos.LeaderboardEntryDTO;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.tbls.WeightedTraitsTable;
import io.tilenft.sql.tbls.WeightlessTraitsTable;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

public class TokenLeaderboardDao {
  private final JdbcTemplate jdbcTemplate;
  private final BeanPropertyRowMapper beanPropertyRowMapper;
  private static final String READ_WEIGHTLESS_TRAIT_BY_TRAIT_TYPE_ID_SQL =
      "SELECT * FROM "
          + WeightlessTraitsTable.TABLE_NAME
          + " WHERE traitTypeId = ? ORDER BY CAST(value AS UNSIGNED) DESC LIMIT ? OFFSET ?";
  private static final String BASE_READ_TOKENS_FOR_A_TOKEN_WITH_BURNT_TRAIT_SQL =
      "SELECT tokenId FROM "
          + WeightedTraitsTable.TABLE_NAME
          + " WHERE traitTypeId = ? AND tokenId IN (";
  private static final int DEFAULT_PAGE_SIZE = 50;

  public TokenLeaderboardDao(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  public List<LeaderboardEntryDTO> getLeaderEntries(
      Long overallRarityTraitTypeId,
      Long isBurntTraitTypeId,
      int numberOfTokensToRetrieve,
      int startIndex) {
    GetLeaderEntriesDTO getLeaderEntriesDTO =
        GetLeaderEntriesDTO.builder().rankCount(0L).prevValue(null).build();
    List<WeightlessTraitDTO> traitsList = new ArrayList<>();
    List<LeaderboardEntryDTO> leaderboardEntriesList = new ArrayList<>();
    int traitsListSize = 0, tokenIdsListSize = 0, prevTraitsListSize;
    do {
      List<WeightlessTraitDTO> highestOverallRarityTraitsList =
          getHighestOverallRarityTraits(overallRarityTraitTypeId, startIndex + traitsListSize);
      if (highestOverallRarityTraitsList.size() == 0) {
        System.out.println("Not able to find the requested number of rarity leader tokens");
        break;
      }
      traitsList.addAll(highestOverallRarityTraitsList);
      leaderboardEntriesList.addAll(
          sortHighestOverallTraitAndGetTokenIds(
              highestOverallRarityTraitsList, isBurntTraitTypeId, getLeaderEntriesDTO));
      prevTraitsListSize = traitsListSize;
      traitsListSize = traitsList.size();
      tokenIdsListSize = leaderboardEntriesList.size();
    } while (tokenIdsListSize < numberOfTokensToRetrieve
        && traitsListSize != 0
        && prevTraitsListSize != traitsListSize);
    if (traitsList.size() == 0) {
      return null;
    }
    return getLeaderTokenIdsSubList(leaderboardEntriesList, numberOfTokensToRetrieve);
  }

  public List<Long> getLeaderTokenIds(
      Long overallRarityTraitTypeId,
      Long isBurntTraitTypeId,
      int numberOfTokensToRetrieve,
      int startIndex) {
    List<LeaderboardEntryDTO> leaderboardEntries =
        getLeaderEntries(
            overallRarityTraitTypeId, isBurntTraitTypeId, numberOfTokensToRetrieve, startIndex);
    List<Long> results = new ArrayList<>();
    for (LeaderboardEntryDTO entry : leaderboardEntries) {
      results.add(entry.getTokenId());
    }
    return results;
  }

  private List<LeaderboardEntryDTO> getLeaderTokenIdsSubList(
      List<LeaderboardEntryDTO> tokenIdsList, int numberOfTokensToRetrieve) {
    return tokenIdsList.subList(0, Math.min(tokenIdsList.size(), numberOfTokensToRetrieve));
  }

  private List<WeightlessTraitDTO> getHighestOverallRarityTraits(
      Long overallRarityTraitTypeId, int sqlOffset) {
    Stream<WeightlessTraitDTO> stream = null;
    try {
      stream =
          jdbcTemplate.queryForStream(
              READ_WEIGHTLESS_TRAIT_BY_TRAIT_TYPE_ID_SQL,
              beanPropertyRowMapper,
              overallRarityTraitTypeId,
              DEFAULT_PAGE_SIZE,
              sqlOffset);
      List<WeightlessTraitDTO> traits = stream.collect(Collectors.toList());
      return traits;
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  private List<LeaderboardEntryDTO> sortHighestOverallTraitAndGetTokenIds(
      List<WeightlessTraitDTO> highestOverallRarityTraitsList,
      Long isBurntTraitTypeId,
      GetLeaderEntriesDTO getLeaderEntriesDTO) {
    Stream<WeightlessTraitDTO> stream = null;
    try {
      List<Long> tokenIds =
          highestOverallRarityTraitsList.stream()
              .map(trait -> trait.getTokenId())
              .collect(Collectors.toList());
      List<LeaderboardEntryDTO> leaderboardEntries =
          highestOverallRarityTraitsList.stream()
              .map(
                  trait ->
                      LeaderboardEntryDTO.builder()
                          .tokenId(trait.getTokenId())
                          .overallRankPoints(trait.getValue())
                          .build())
              .collect(Collectors.toList());
      List<Object> queryValuesList = new ArrayList<>();
      queryValuesList.add(isBurntTraitTypeId);
      queryValuesList.addAll(tokenIds);
      stream =
          jdbcTemplate.queryForStream(
              getTokenIdsWithBurntTraitSql(highestOverallRarityTraitsList.size()),
              beanPropertyRowMapper,
              queryValuesList.toArray());
      List<Long> foundBurntTokenIds =
          stream.map(trait -> trait.getTokenId()).collect(Collectors.toList());
      List<LeaderboardEntryDTO> results = new ArrayList<>();
      for (LeaderboardEntryDTO entry : leaderboardEntries) {
        if (foundBurntTokenIds.contains(entry.getTokenId())) {
          // Burn token, do not add to results
          continue;
        }
        Long rankCount = getLeaderEntriesDTO.getRankCount();
        if (!entry.getOverallRankPoints().equals(getLeaderEntriesDTO.getPrevValue())) {
          rankCount++;
        }
        entry.setRankCount(rankCount);
        results.add(entry);
        getLeaderEntriesDTO.setRankCount(rankCount);
        getLeaderEntriesDTO.setPrevValue(entry.getOverallRankPoints());
      }
      return results;
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }

  private String getTokenIdsWithBurntTraitSql(int numberOfTokenIdsToLookFor) {
    String sql = BASE_READ_TOKENS_FOR_A_TOKEN_WITH_BURNT_TRAIT_SQL;
    for (int x = 0; x < numberOfTokenIdsToLookFor; x++) {
      if (x > 0) {
        sql = sql + ", ";
      }
      sql = sql + "?";
    }
    sql = sql + ")";
    return sql;
  }
}
