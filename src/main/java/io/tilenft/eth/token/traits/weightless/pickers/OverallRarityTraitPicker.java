package io.tilenft.eth.token.traits.weightless.pickers;

import io.tilenft.eth.token.traits.weightless.OverallRarityCalculator;
import org.springframework.beans.factory.annotation.Autowired;

public class OverallRarityTraitPicker implements WeightlessTraitPickerInterface {

  @Autowired private OverallRarityCalculator overallRarityCalculator;

  @Override
  public String getValue(WeightlessTraitPickerContext context)
      throws WeightlessTraitPickerException {
    if (context.getWeightlessTraits().size() == 0) {
      return overallRarityCalculator
          .calculateRarity(context.getWeightedTraits(), context.getWeightedTraitTypeWeights())
          .toString();
    }
    return overallRarityCalculator
        .calculateRarity(
            context.getWeightedTraits(),
            context.getWeightedTraitTypeWeights(),
            context.getWeightlessTraits())
        .toString();
  }

  @Override
  public String getDisplayValue(WeightlessTraitPickerContext context)
      throws WeightlessTraitPickerException {
    return "";
  }
}
