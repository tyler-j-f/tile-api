package io.tilenft.config.beans;

import io.tilenft.eth.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
import io.tilenft.eth.token.traits.weightless.OverallRarityCalculator;
import io.tilenft.eth.token.traits.weightless.pickers.ColorTraitPicker;
import io.tilenft.eth.token.traits.weightless.pickers.EmojiTraitPicker;
import io.tilenft.eth.token.traits.weightless.pickers.MergeRarityTraitPicker;
import io.tilenft.eth.token.traits.weightless.pickers.OverallRarityTraitPicker;
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
