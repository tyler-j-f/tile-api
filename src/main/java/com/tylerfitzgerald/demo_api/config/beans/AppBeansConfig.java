package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.config.external.EnvConfig;
import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.MergeTokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.OverallRarityCalculator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.ColorTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.MergeRarityTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.OverallRarityTraitPicker;
import com.tylerfitzgerald.demo_api.etc.BigIntegerFactory;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypeWeightsListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightlessTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ResourceLoader;
import org.web3j.protocol.Web3j;
import org.web3j.protocol.http.HttpService;

@Configuration
public class AppBeansConfig {

  @Autowired private EnvConfig envConfig;
  @Autowired private ResourceLoader resourceLoader;
  @Autowired TokenConfig tokenConfig;
  @Autowired WeightlessTraitTypesListFinder weightlessTraitTypesListFinder;
  @Autowired WeightedTraitTypesListFinder weightedTraitTypesListFinder;
  @Autowired WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;

  @Bean
  public Web3j web3j() {
    return Web3j.build(new HttpService(envConfig.getAlchemyURI()));
  }

  @Bean
  public TokenInitializer tokenInitializer() {
    return new TokenInitializer(initializeTokenWeightlessTraitsCreator(), weightedTraitsCreator());
  }

  @Bean
  public MergeTokenInitializer mergeTokenInitializer() {
    return new MergeTokenInitializer(mergeTokenWeightlessTraitsCreator(), weightedTraitsCreator());
  }

  @Bean
  public InitializeTokenWeightlessTraitsCreator initializeTokenWeightlessTraitsCreator() {
    return new InitializeTokenWeightlessTraitsCreator();
  }

  @Bean
  public MergeTokenWeightlessTraitsCreator mergeTokenWeightlessTraitsCreator() {
    return new MergeTokenWeightlessTraitsCreator();
  }

  @Bean
  public TokenRetriever tokenRetriever() {
    return new TokenRetriever();
  }

  @Bean
  public ImageResourcesLoader imageResourcesLoader() {
    return new ImageResourcesLoader(resourceLoader, "classpath:openmoji/*.png");
  }

  @Bean
  public EmojiTraitPicker emojiPickerTrait() {
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

  @Bean
  public WeightedTraitsCreator weightedTraitsCreator() {
    return new WeightedTraitsCreator();
  }
}
