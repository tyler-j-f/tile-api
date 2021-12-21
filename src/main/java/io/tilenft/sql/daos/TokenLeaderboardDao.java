package io.tilenft.sql.daos;

import io.tilenft.sql.dtos.TokenLeaderboardEntryDTO;
import io.tilenft.sql.tbls.WeightedTraitsTable;
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
      "SELECT DISTINCT weightless.id as weightlessId, weightless.traitId as weightlessTraitId, weightless.tokenId as weightlessTokenId, weightless.traitTypeId as weightlessTraitTypeId, weightless.value as weightlessValue, weightless.displayTypeValue as weightlessDisplayTypeValue, weighted.id as weightedId,  weighted.traitId as weightedTraitId,  weighted.tokenId as weightedTokenId,  weighted.traitTypeId as weightedTraitTypeId,  weighted.traitTypeWeightId as weightedTraitTypeWeightId FROM "
          + WeightlessTraitsTable.TABLE_NAME
          + " weightless"
          + " INNER JOIN "
          + WeightedTraitsTable.TABLE_NAME
          + " weighted"
          + " ON weightless.tokenId = weighted.tokenId"
          + " WHERE weightless.traitTypeId = ? ORDER BY CAST(weightless.value AS UNSIGNED) DESC LIMIT ?";

  public TokenLeaderboardDao(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  public List<Long> getLeaderTokenIds(
      Long overallRarityTraitTypeId, Long isBurntTraitTypeId, int rowLimit) {
    Stream<TokenLeaderboardEntryDTO> stream = null;
    try {
      stream =
          jdbcTemplate.queryForStream(
              READ_BY_TRAIT_TYPE_ID_SQL, beanPropertyRowMapper, overallRarityTraitTypeId, rowLimit);
      List<TokenLeaderboardEntryDTO> traits = stream.collect(Collectors.toList());
      if (traits.size() == 0) {
        return null;
      }
      return traits.stream()
          .map(trait -> trait.getWeightlessTokenId())
          .collect(Collectors.toList());
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }
}
