package io.tileNft.config.beans;

import io.tileNft.erc721.token.TokenFacade;
import io.tileNft.erc721.token.TokenRetriever;
import io.tileNft.erc721.token.initializers.MergeTokenInitializer;
import io.tileNft.erc721.token.initializers.TokenInitializer;
import io.tileNft.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tileNft.erc721.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import io.tileNft.erc721.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
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
