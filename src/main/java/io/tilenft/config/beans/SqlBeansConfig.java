package io.tilenft.config.beans;

import io.tilenft.sql.daos.TokenLeaderboardDao;
import io.tilenft.sql.dtos.TokenDTO;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import io.tilenft.sql.repositories.TokenRepository;
import io.tilenft.sql.repositories.WeightedTraitRepository;
import io.tilenft.sql.repositories.WeightedTraitTypeRepository;
import io.tilenft.sql.repositories.WeightedTraitTypeWeightRepository;
import io.tilenft.sql.repositories.WeightlessTraitRepository;
import io.tilenft.sql.repositories.WeightlessTraitTypeRepository;
import io.tilenft.sql.tbls.TokenTable;
import io.tilenft.sql.tbls.WeightedTraitTypeWeightsTable;
import io.tilenft.sql.tbls.WeightedTraitTypesTable;
import io.tilenft.sql.tbls.WeightedTraitsTable;
import io.tilenft.sql.tbls.WeightlessTraitTypesTable;
import io.tilenft.sql.tbls.WeightlessTraitsTable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;

@Configuration
public class SqlBeansConfig {
  private final JdbcTemplate jdbcTemplate;

  public SqlBeansConfig(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Bean
  public TokenRepository tokenRepository() {
    return new TokenRepository(jdbcTemplate, new BeanPropertyRowMapper(TokenDTO.class));
  }

  @Bean
  public TokenTable tokenTable() {
    return new TokenTable(jdbcTemplate);
  }

  @Bean
  public WeightedTraitTypesTable traitTypesTable() {
    return new WeightedTraitTypesTable(jdbcTemplate);
  }

  @Bean
  public WeightedTraitTypeRepository weightedTraitTypeRepository() {
    return new WeightedTraitTypeRepository(
        jdbcTemplate, new BeanPropertyRowMapper(WeightedTraitTypeDTO.class));
  }

  @Bean
  public WeightedTraitTypeWeightsTable traitTypeWeightsTable() {
    return new WeightedTraitTypeWeightsTable(jdbcTemplate);
  }

  @Bean
  public WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository() {
    return new WeightedTraitTypeWeightRepository(
        jdbcTemplate, new BeanPropertyRowMapper(WeightedTraitTypeWeightDTO.class));
  }

  @Bean
  public WeightedTraitsTable traitsTable() {
    return new WeightedTraitsTable(jdbcTemplate);
  }

  @Bean
  public WeightedTraitRepository weightedTraitRepository() {
    return new WeightedTraitRepository(
        jdbcTemplate, new BeanPropertyRowMapper(WeightedTraitDTO.class));
  }

  @Bean
  public WeightlessTraitsTable weightlessTraitsTable() {
    return new WeightlessTraitsTable(jdbcTemplate);
  }

  @Bean
  public WeightlessTraitTypesTable weightlessTraitTypesTable() {
    return new WeightlessTraitTypesTable(jdbcTemplate);
  }

  @Bean
  public WeightlessTraitRepository weightlessTraitRepository() {
    return new WeightlessTraitRepository(
        jdbcTemplate, new BeanPropertyRowMapper(WeightlessTraitDTO.class));
  }

  @Bean
  public WeightlessTraitTypeRepository weightlessTraitTypeRepository() {
    return new WeightlessTraitTypeRepository(
        jdbcTemplate, new BeanPropertyRowMapper(WeightlessTraitTypeDTO.class));
  }

  @Bean
  public TokenLeaderboardDao tokenLeaderboardDao() {
    return new TokenLeaderboardDao(
        jdbcTemplate, new BeanPropertyRowMapper(WeightlessTraitDTO.class));
  }
}
