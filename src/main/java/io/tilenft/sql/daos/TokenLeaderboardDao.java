package io.tilenft.sql.daos;

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
  public static final String READ_WEIGHTLESS_TRAIT_BY_TRAIT_TYPE_ID_SQL =
      "SELECT * FROM "
          + WeightlessTraitsTable.TABLE_NAME
          + " WHERE traitTypeId = ? ORDER BY CAST(value AS UNSIGNED) DESC LIMIT ? OFFSET ?";
  public static final String BASE_READ_TOKENS_FOR_A_TOKEN_WITH_BURNT_TRAIT_SQL =
      "SELECT tokenId FROM "
          + WeightedTraitsTable.TABLE_NAME
          + " WHERE traitTypeId = ? AND tokenId IN (";
  private static final int DEFAULT_PAGE_SIZE = 5;

  public TokenLeaderboardDao(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  public List<Long> getLeaderTokenIds(
      Long overallRarityTraitTypeId,
      Long isBurntTraitTypeId,
      int numberOfTokensToRetrieve,
      int startIndex) {
    List<WeightlessTraitDTO> traitsList = new ArrayList<>();
    List<Long> tokenIdsList = new ArrayList<>();
    int traitsListSize = 0, tokenIdsListSize = 0, prevTraitsListSize;
    do {
      List<WeightlessTraitDTO> highestOverallRarityTraitsList =
          getHighestOverallRarityTraits(overallRarityTraitTypeId, startIndex + traitsListSize);
      traitsList.addAll(highestOverallRarityTraitsList);
      tokenIdsList.addAll(
          sortHighestOverallTraitAndGetTokenIds(
              highestOverallRarityTraitsList, isBurntTraitTypeId));
      prevTraitsListSize = traitsListSize;
      traitsListSize = traitsList.size();
      tokenIdsListSize = tokenIdsList.size();
    } while (tokenIdsListSize < numberOfTokensToRetrieve
        && traitsListSize != 0
        && prevTraitsListSize != traitsListSize);
    if (traitsList.size() == 0) {
      return null;
    }
    return traitsList.stream().map(trait -> trait.getTokenId()).collect(Collectors.toList());
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

  private List<Long> sortHighestOverallTraitAndGetTokenIds(
      List<WeightlessTraitDTO> highestOverallRarityTraitsList, Long isBurntTraitTypeId) {
    Stream<WeightlessTraitDTO> stream = null;
    try {
      System.out.println(
          "DEBUG sortHighestOverallTraitAndGetTokenIds: " + highestOverallRarityTraitsList);
      List<Object> updateValuesList =
          highestOverallRarityTraitsList.stream()
              .map(trait -> trait.getTokenId())
              .collect(Collectors.toList());
      System.out.println("DEBUG updateValuesList: " + updateValuesList);
      System.out.println("DEBUG updateValuesList 2: " + updateValuesList.toArray());
      stream =
          jdbcTemplate.queryForStream(
              getTokenIdsWithBurntTraitSql(highestOverallRarityTraitsList.size()),
              beanPropertyRowMapper,
              isBurntTraitTypeId,
              updateValuesList.toArray());
      List<Long> tokenIds = stream.map(trait -> trait.getTokenId()).collect(Collectors.toList());
      System.out.println("DEBUG long list: " + tokenIds);
      return tokenIds;
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
    System.out.println("DEBUG sql: " + sql);
    return sql;
  }
}
