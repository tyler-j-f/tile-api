package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tbls.TokenTable;
import com.tylerfitzgerald.demo_api.sql.tbls.WeightedTraitTypeWeightsTable;
import com.tylerfitzgerald.demo_api.sql.tbls.WeightedTraitTypesTable;
import com.tylerfitzgerald.demo_api.sql.tbls.WeightedTraitsTable;
import com.tylerfitzgerald.demo_api.sql.tbls.WeightlessTraitTypesTable;
import com.tylerfitzgerald.demo_api.sql.tbls.WeightlessTraitsTable;
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
