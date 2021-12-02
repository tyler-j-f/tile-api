package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.erc721.token.finders.WeightedTraitTypeWeightsListFinder;
import com.tylerfitzgerald.demo_api.erc721.token.finders.WeightedTraitsListFinder;
import com.tylerfitzgerald.demo_api.erc721.token.finders.WeightlessTraitsListFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListFindersBeansConfig {

  @Bean
  public WeightlessTraitsListFinder weightlessTraitInListFinder() {
    return new WeightlessTraitsListFinder();
  }

  @Bean
  public WeightedTraitsListFinder weightedTraitInListFinder() {
    return new WeightedTraitsListFinder();
  }

  @Bean
  public WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightListFinder() {
    return new WeightedTraitTypeWeightsListFinder();
  }
}
