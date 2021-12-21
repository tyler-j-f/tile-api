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

  public TokenLeaderboardDao(
      JdbcTemplate jdbcTemplate, BeanPropertyRowMapper beanPropertyRowMapper) {
    this.jdbcTemplate = jdbcTemplate;
    this.beanPropertyRowMapper = beanPropertyRowMapper;
  }

  public List<Long> getLeaderTokenIds(Long traitTypeId, int count) {
    Stream<WeightlessTraitDTO> stream = null;
    try {
      stream =
          jdbcTemplate.queryForStream(
              READ_BY_TRAIT_TYPE_ID_SQL, beanPropertyRowMapper, traitTypeId, count);
      List<WeightlessTraitDTO> traits = stream.collect(Collectors.toList());
      if (traits.size() == 0) {
        return null;
      }
      return traits.stream().map(trait -> trait.getTokenId()).collect(Collectors.toList());
    } finally {
      if (stream != null) {
        stream.close();
      }
    }
  }
}
