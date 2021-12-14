package com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers;

import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.OverallRarityCalculator;
import org.springframework.beans.factory.annotation.Autowired;

public class OverallRarityTraitPickerPicker implements WeightlessTraitPickerInterface {

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
