package io.tilenft.sql.daos;

import io.tilenft.sql.dtos.WeightlessTraitDTO;
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
  public static final String READ_BY_TRAIT_TYPE_ID_SQL =
      "SELECT * FROM "
          + WeightlessTraitsTable.TABLE_NAME
          + " WHERE traitTypeId = ? ORDER BY CAST(value AS UNSIGNED) DESC LIMIT ? OFFSET ?";
  private static final int DEFAULT_PAGE_SIZE = 20;

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
          highestOverallRarityTraitsList.stream()
              .map(trait -> trait.getTokenId())
              .collect(Collectors.toList()));
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
              READ_BY_TRAIT_TYPE_ID_SQL,
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
}
