package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypeWeightsListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitsListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightlessTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightlessTraitsListFinder;
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
