package com.tylerfitzgerald.demo_api.config;

import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import com.tylerfitzgerald.demo_api.sql.tables.TraitTypeWeightsTable;
import com.tylerfitzgerald.demo_api.sql.tables.TraitsTable;
import com.tylerfitzgerald.demo_api.sql.tokens.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tokens.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.tables.TokenTable;
import com.tylerfitzgerald.demo_api.sql.nft.NFTInitializer;
import com.tylerfitzgerald.demo_api.erc721.NFTDataRetriever;
import com.tylerfitzgerald.demo_api.sql.traitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.traitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.traitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.traitTypes.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tables.TraitTypesTable;
import com.tylerfitzgerald.demo_api.sql.traits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.traits.TraitRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class AppConfig {

  @Autowired private EnvConfig appConfig;

  private final JdbcTemplate jdbcTemplate;

  public AppConfig(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Bean
  public TokenRepository tokenRepository() {
    return new TokenRepository(jdbcTemplate, new BeanPropertyRowMapper(TokenDTO.class));
  }

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(appConfig.getAlchemyURI()));
  }

  @Bean
  public TokenTable tokenTable() {
    return new TokenTable(jdbcTemplate);
  }

  @Bean
  public MintEventRetriever mintEventRetriever() {
    return new MintEventRetriever();
  }

  @Bean
  public TraitTypesTable traitTypesTable() {
    return new TraitTypesTable(jdbcTemplate);
  }

  @Bean
  public TraitTypeRepository traitTypeRepository() {
    return new TraitTypeRepository(jdbcTemplate, new BeanPropertyRowMapper(TraitTypeDTO.class));
  }

  @Bean
  public TraitTypeWeightsTable traitTypeWeightsTable() {
    return new TraitTypeWeightsTable(jdbcTemplate);
  }

  @Bean
  public TraitTypeWeightRepository traitTypeWeightRepository() {
    return new TraitTypeWeightRepository(
        jdbcTemplate, new BeanPropertyRowMapper(TraitTypeWeightDTO.class));
  }

  @Bean
  public TraitsTable traitsTable() {
    return new TraitsTable(jdbcTemplate);
  }

  @Bean
  public TraitRepository traitRepository() {
    return new TraitRepository(jdbcTemplate, new BeanPropertyRowMapper(TraitDTO.class));
  }

  @Bean
  public NFTInitializer nftInitializer() {
    return new NFTInitializer();
  }

  @Bean
  public NFTDataRetriever nftRetriever() {
    return new NFTDataRetriever();
  }
}
