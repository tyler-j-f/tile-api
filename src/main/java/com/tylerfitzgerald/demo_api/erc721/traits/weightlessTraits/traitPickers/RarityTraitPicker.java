package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers;

import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitInterface;
import org.springframework.beans.factory.annotation.Autowired;

public class RarityTraitPicker implements WeightlessTraitInterface {

  @Autowired private RarityCalculator rarityCalculator;

  @Override
  public String getValue(WeightlessTraitContext context) throws WeightlessTraitException {
    return rarityCalculator
        .calculateRarity(context.getWeightedTraits(), context.getTraitTypeWeights())
        .toString();
  }

  @Override
  public String getDisplayValue(WeightlessTraitContext context) throws WeightlessTraitException {
    return "";
  }
}
