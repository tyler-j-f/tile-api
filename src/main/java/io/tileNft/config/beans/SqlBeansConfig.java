package io.tileNft.config.beans;

import io.tileNft.sql.dtos.TokenDTO;
import io.tileNft.sql.dtos.WeightedTraitDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeDTO;
import io.tileNft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tileNft.sql.dtos.WeightlessTraitDTO;
import io.tileNft.sql.dtos.WeightlessTraitTypeDTO;
import io.tileNft.sql.repositories.TokenRepository;
import io.tileNft.sql.repositories.WeightedTraitRepository;
import io.tileNft.sql.repositories.WeightedTraitTypeRepository;
import io.tileNft.sql.repositories.WeightedTraitTypeWeightRepository;
import io.tileNft.sql.repositories.WeightlessTraitRepository;
import io.tileNft.sql.repositories.WeightlessTraitTypeRepository;
import io.tileNft.sql.tbls.TokenTable;
import io.tileNft.sql.tbls.WeightedTraitTypeWeightsTable;
import io.tileNft.sql.tbls.WeightedTraitTypesTable;
import io.tileNft.sql.tbls.WeightedTraitsTable;
import io.tileNft.sql.tbls.WeightlessTraitTypesTable;
import io.tileNft.sql.tbls.WeightlessTraitsTable;
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
}
