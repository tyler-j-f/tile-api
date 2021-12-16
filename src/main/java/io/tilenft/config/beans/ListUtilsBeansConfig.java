package io.tilenft.config.beans;

import io.tilenft.etc.lists.finders.WeightedTraitTypeWeightsListFinder;
import io.tilenft.etc.lists.finders.WeightedTraitTypesListFinder;
import io.tilenft.etc.lists.finders.WeightedTraitsListFinder;
import io.tilenft.etc.lists.finders.WeightlessTraitTypesListFinder;
import io.tilenft.etc.lists.finders.WeightlessTraitsListFinder;
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
