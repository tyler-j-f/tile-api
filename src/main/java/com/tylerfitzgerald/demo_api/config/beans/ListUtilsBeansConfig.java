package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.listUtils.finders.WeightedTraitTypeWeightsListHelper;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightedTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightedTraitsListHelper;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightlessTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightlessTraitsListFinder;
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
  public WeightedTraitsListHelper weightedTraitInListFinder() {
    return new WeightedTraitsListHelper();
  }

  @Bean
  public WeightedTraitTypeWeightsListHelper weightedTraitTypeWeightListFinder() {
    return new WeightedTraitTypeWeightsListHelper();
  }

  @Bean
  public WeightedTraitTypesListFinder weightedTraitTypesListFinder() {
    return new WeightedTraitTypesListFinder();
  }
}
