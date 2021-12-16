package io.tilenft.config.beans;

import io.tilenft.etc.lsitFinders.WeightedTraitTypeWeightsListFinder;
import io.tilenft.etc.lsitFinders.WeightedTraitTypesListFinder;
import io.tilenft.etc.lsitFinders.WeightedTraitsListFinder;
import io.tilenft.etc.lsitFinders.WeightlessTraitTypesListFinder;
import io.tilenft.etc.lsitFinders.WeightlessTraitsListFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListUtilsBeansConfig {

  @Bean
  public WeightlessTraitsListFinder weightlessTraitInListFinder() {
    return new WeightlessTraitsListFinder();
  }

  @Bean
  public WeightlessTraitTypesListFinder WeightlessTraitTypesListFinder() {
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
