package com.tylerfitzgerald.demo_api.config;

import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import com.tylerfitzgerald.demo_api.sql.TraitTypeWeightsTable;
import com.tylerfitzgerald.demo_api.sql.TraitsTable;
import com.tylerfitzgerald.demo_api.sql.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.TokenTable;
import com.tylerfitzgerald.demo_api.sql.nft.NFTInitializer;
import com.tylerfitzgerald.demo_api.erc721.NFTDataRetriever;
import com.tylerfitzgerald.demo_api.sql.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.TraitTypesTable;
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
