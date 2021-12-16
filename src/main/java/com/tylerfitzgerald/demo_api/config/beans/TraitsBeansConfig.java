package com.tylerfitzgerald.demo_api.config.beans;

import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.OverallRarityCalculator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.ColorTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.MergeRarityTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.OverallRarityTraitPicker;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class TraitsBeansConfig {

  @Bean
  public EmojiTraitPicker emojiPickerTrait() {
    return new EmojiTraitPicker();
  }

  @Bean
  public ColorTraitPicker colorTraitPicker() {
    return new ColorTraitPicker();
  }

  @Bean
  public MergeRarityTraitPicker mergeRarityTraitPicker() {
    return new MergeRarityTraitPicker();
  }

  @Bean
  public OverallRarityTraitPicker overallRarityTraitPicker() {
    return new OverallRarityTraitPicker();
  }

  @Bean
  public InitializeTokenWeightlessTraitsCreator initializeTokenWeightlessTraitsCreator() {
    return new InitializeTokenWeightlessTraitsCreator();
  }

  @Bean
  public MergeTokenWeightlessTraitsCreator mergeTokenWeightlessTraitsCreator() {
    return new MergeTokenWeightlessTraitsCreator();
  }

  @Bean
  public WeightedTraitsCreator weightedTraitsCreator() {
    return new WeightedTraitsCreator();
  }

  @Bean
  public OverallRarityCalculator rarityCalculator() {
    return new OverallRarityCalculator();
  }
}
