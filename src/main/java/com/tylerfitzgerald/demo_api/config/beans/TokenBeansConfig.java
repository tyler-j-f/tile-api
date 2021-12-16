package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenRetriever;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.MergeTokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializer;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TokenBeansConfig {

  @Autowired InitializeTokenWeightlessTraitsCreator initializeTokenWeightlessTraitsCreator;
  @Autowired MergeTokenWeightlessTraitsCreator mergeTokenWeightlessTraitsCreator;
  @Autowired WeightedTraitsCreator weightedTraitsCreator;

  @Bean
  public TokenInitializer tokenInitializer() {
    return new TokenInitializer(initializeTokenWeightlessTraitsCreator, weightedTraitsCreator);
  }

  @Bean
  public MergeTokenInitializer mergeTokenInitializer() {
    return new MergeTokenInitializer(mergeTokenWeightlessTraitsCreator, weightedTraitsCreator);
  }

  @Bean
  public TokenRetriever tokenRetriever() {
    return new TokenRetriever();
  }

  @Bean
  public TokenFacade tokenFacade() {
    return new TokenFacade();
  }
}
