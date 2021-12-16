package io.tileNft.config.beans;

import io.tileNft.etc.listFinders.WeightedTraitTypeWeightsListFinder;
import io.tileNft.etc.listFinders.WeightedTraitTypesListFinder;
import io.tileNft.etc.listFinders.WeightedTraitsListFinder;
import io.tileNft.etc.listFinders.WeightlessTraitTypesListFinder;
import io.tileNft.etc.listFinders.WeightlessTraitsListFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListUtilsBeansConfig {

  @Bean
  public WeightlessTraitsListFinder weightlessTraitInListFinder() {
    return new WeightlessTraitsListFinder();
  }

  @Bean
  public WeightlessTraitTypesListFinder weightlessTraitTypesListFinder() {
    return new WeightlessTraitTypesListFinder();
  }

  @Bean
  public WeightedTraitsListFinder weightedTraitInListFinder() {
    return new WeightedTraitsListFinder();
  }

  @Bean
  public WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightListFinder() {
    return new WeightedTraitTypeWeightsListFinder();
  }

  @Bean
  public WeightedTraitTypesListFinder weightedTraitTypesListFinder() {
    return new WeightedTraitTypesListFinder();
  }
}
