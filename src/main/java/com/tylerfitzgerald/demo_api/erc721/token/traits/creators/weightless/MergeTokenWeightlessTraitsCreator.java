package com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.MergeRarityTraitPickerPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.WeightlessTraitPickerContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.WeightlessTraitPickerException;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightlessTraitsListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class MergeTokenWeightlessTraitsCreator extends AbstractWeightlessTraitsCreator {

  @Autowired private WeightlessTraitsListFinder weightlessTraitInListFinder;
  @Autowired private MergeRarityTraitPickerPicker mergeRarityTraitPicker;

  @Override
  protected String getWeightlessTraitValue(WeightlessTraitTypeDTO weightlessTraitType)
      throws WeightlessTraitPickerException {
    int traitTypeId = Math.toIntExact(weightlessTraitType.getWeightlessTraitTypeId());
    switch (traitTypeId) {
      case WeightlessTraitTypeConstants.TILE_1_RARITY:
      case WeightlessTraitTypeConstants.TILE_2_RARITY:
      case WeightlessTraitTypeConstants.TILE_3_RARITY:
      case WeightlessTraitTypeConstants.TILE_4_RARITY:
        return mergeRarityTraitPicker.getValue(
            WeightlessTraitPickerContext.builder()
                .traitTypeId(traitTypeId)
                .burnedNft1(context.getBurnedNft1())
                .burnedNft2(context.getBurnedNft2())
                .build());
      case WeightlessTraitTypeConstants.OVERALL_RARITY:
        return overallRarityTraitPicker.getValue(
            WeightlessTraitPickerContext.builder()
                .seedForTrait(null)
                .weightedTraits(context.getWeightedTraits())
                .weightedTraitTypeWeights(context.getWeightedTraitTypeWeights())
                .weightlessTraits(getCreatedWeightlessTraits())
                .build());
      case WeightlessTraitTypeConstants.TILE_1_EMOJI:
      case WeightlessTraitTypeConstants.TILE_2_EMOJI:
      case WeightlessTraitTypeConstants.TILE_3_EMOJI:
      case WeightlessTraitTypeConstants.TILE_4_EMOJI:
      case WeightlessTraitTypeConstants.TILE_1_COLOR:
      case WeightlessTraitTypeConstants.TILE_2_COLOR:
      case WeightlessTraitTypeConstants.TILE_3_COLOR:
      case WeightlessTraitTypeConstants.TILE_4_COLOR:
        return findWeightlessTraitValueFromListByType(context.getBurnedNft1(), (long) traitTypeId);
      default:
        System.out.println("ERROR: Invalid mergeWeightlessTraitValue. traitTypeId: " + traitTypeId);
        return "invalid mergeWeightlessTraitValue";
    }
  }

  private String findWeightlessTraitValueFromListByType(TokenFacadeDTO nft, Long traitTypeId) {
    WeightlessTraitDTO weightedTrait =
        weightlessTraitInListFinder.findFirstByTraitTypeId(nft.getWeightlessTraits(), traitTypeId);
    if (weightedTrait != null) {
      return weightedTrait.getValue();
    }
    return null;
  }
}
