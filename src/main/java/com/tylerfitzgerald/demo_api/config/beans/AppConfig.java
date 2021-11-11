package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.events.MintEventRetriever;
import com.tylerfitzgerald.demo_api.scheduler.tasks.HandleMintEvents;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightsTable;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitsTable;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenTable;
import com.tylerfitzgerald.demo_api.erc721.token.TokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.TraitTypesTable;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitRepository;
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
  public TokenInitializer nftInitializer() {
    return new TokenInitializer();
  }

  @Bean
  public TokenRetriever tokenRetriever() {
    return new TokenRetriever();
  }

  @Bean
  public HandleMintEvents handleMintEventsAndCreateDBTokensTask() {
    return new HandleMintEvents();
  }
}
