package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.config.EnvConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.ColorTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.MergeRarityTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.OverallRarityCalculator;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.OverallRarityTraitPicker;
import com.tylerfitzgerald.demo_api.ethEvents.etc.BigIntegerFactory;
import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.tblToken.TokenTable;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightsTable;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypesTable;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitRepository;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitsTable;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypesTable;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitRepository;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitsTable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class AppBeansConfig {

  @Autowired private EnvConfig envConfig;

  @Autowired private ResourceLoader resourceLoader;

  private final JdbcTemplate jdbcTemplate;

  public AppBeansConfig(JdbcTemplate jdbcTemplate) {
    this.jdbcTemplate = jdbcTemplate;
  }

  @Bean
  public TokenRepository tokenRepository() {
    return new TokenRepository(jdbcTemplate, new BeanPropertyRowMapper(TokenDTO.class));
  }

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(envConfig.getAlchemyURI()));
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
  public WeightedTraitTypeRepository traitTypeRepository() {
    return new WeightedTraitTypeRepository(
        jdbcTemplate, new BeanPropertyRowMapper(WeightedTraitTypeDTO.class));
  }

  @Bean
  public WeightedTraitTypeWeightsTable traitTypeWeightsTable() {
    return new WeightedTraitTypeWeightsTable(jdbcTemplate);
  }

  @Bean
  public WeightedTraitTypeWeightRepository traitTypeWeightRepository() {
    return new WeightedTraitTypeWeightRepository(
        jdbcTemplate, new BeanPropertyRowMapper(WeightedTraitTypeWeightDTO.class));
  }

  @Bean
  public WeightedTraitsTable traitsTable() {
    return new WeightedTraitsTable(jdbcTemplate);
  }

  @Bean
  public WeightedTraitRepository traitRepository() {
    return new WeightedTraitRepository(
        jdbcTemplate, new BeanPropertyRowMapper(WeightedTraitDTO.class));
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
  public ImageResourcesLoader imageResourcesLoader() {
    return new ImageResourcesLoader(resourceLoader, "classpath:openmoji/*.png");
  }

  @Bean
  public EmojiTraitPicker emojiiPickerTrait() {
    return new EmojiTraitPicker();
  }

  @Bean
  public ColorTraitPicker colorTraitPicker() {
    return new ColorTraitPicker();
  }

  @Bean
  public MergeRarityTraitPicker mergeRarityTraitPicker() {
    return new MergeRarityTraitPicker();
  }

  @Bean
  public OverallRarityTraitPicker overallRarityTraitPicker() {
    return new OverallRarityTraitPicker();
  }

  @Bean
  public OverallRarityCalculator rarityCalculator() {
    return new OverallRarityCalculator();
  }

  @Bean
  public BigIntegerFactory bigIntegerFactory() {
    return new BigIntegerFactory();
  }

  @Bean
  public TokenFacade tokenFacade() {
    return new TokenFacade();
  }
}
