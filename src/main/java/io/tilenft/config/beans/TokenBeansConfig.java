package io.tilenft.config.beans;

import io.tilenft.eth.token.TokenFacade;
import io.tilenft.eth.token.TokenLeaderboardRetriever;
import io.tilenft.eth.token.TokenRetriever;
import io.tilenft.eth.token.initializers.MergeTokenHandler;
import io.tilenft.eth.token.initializers.MergeTokenInitializer;
import io.tilenft.eth.token.initializers.TokenInitializer;
import io.tilenft.eth.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
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
  public MergeTokenHandler mergeTokenHandler() {
    return new MergeTokenHandler();
  }

  @Bean
  public TokenRetriever tokenRetriever() {
    return new TokenRetriever();
  }

  @Bean
  public TokenLeaderboardRetriever tokenLeaderboardRetriever() {
    return new TokenLeaderboardRetriever();
  }

  @Bean
  public TokenFacade tokenFacade() {
    return new TokenFacade();
  }
}
