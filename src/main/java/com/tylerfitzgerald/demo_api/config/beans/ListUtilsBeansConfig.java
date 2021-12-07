package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypeWeightsFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypesFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitsFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitTypesFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitsFinder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ListUtilsBeansConfig {

  @Bean
  public WeightlessTraitsFinder weightlessTraitInListFinder() {
    return new WeightlessTraitsFinder();
  }

  @Bean
  public WeightlessTraitTypesFinder WeightlessTraitTypesListFinder() {
    return new WeightlessTraitTypesFinder();
  }

  @Bean
  public WeightedTraitsFinder weightedTraitInListFinder() {
    return new WeightedTraitsFinder();
  }

  @Bean
  public WeightedTraitTypeWeightsFinder weightedTraitTypeWeightListFinder() {
    return new WeightedTraitTypeWeightsFinder();
  }

  @Bean
  public WeightedTraitTypesFinder weightedTraitTypesListFinder() {
    return new WeightedTraitTypesFinder();
  }
}
