package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers;

import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitInterface;
import org.springframework.beans.factory.annotation.Autowired;

public class OverallRarityTraitPicker implements WeightlessTraitInterface {

  @Autowired private OverallRarityCalculator overallRarityCalculator;

  @Override
  public String getValue(WeightlessTraitContext context) throws WeightlessTraitException {
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
  public String getDisplayValue(WeightlessTraitContext context) throws WeightlessTraitException {
    return "";
  }
}
