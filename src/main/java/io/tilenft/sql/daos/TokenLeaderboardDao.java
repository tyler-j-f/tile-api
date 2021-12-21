package io.tilenft.sql.daos;

import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.tbls.WeightlessTraitsTable;
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
          + " WHERE traitTypeId = ? ORDER BY CAST(value AS UNSIGNED) DESC LIMIT ? ";
  private static final int DEFAULT_PAGE_SIZE = 5;
  public TokenLeaderboardDao(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  public List<Long> getLeaderTokenIds(
      Long overallRarityTraitTypeId, Long isBurntTraitTypeId, int numberOfTokensToRetrieve) {
    List<WeightlessTraitDTO> traits = getFromDB(overallRarityTraitTypeId);
    if (traits.size() == 0) {
      return null;
    }
    return traits.stream().map(trait -> trait.getTokenId()).collect(Collectors.toList());
  }

  private List<WeightlessTraitDTO> getFromDB(
      Long overallRarityTraitTypeId
  ) {
    Stream<WeightlessTraitDTO> stream = null;
    try {
      stream = jdbcTemplate.queryForStream(
          READ_BY_TRAIT_TYPE_ID_SQL, beanPropertyRowMapper, overallRarityTraitTypeId, DEFAULT_PAGE_SIZE);
      List<WeightlessTraitDTO> traits = stream.collect(Collectors.toList());
      return traits;
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }
}
