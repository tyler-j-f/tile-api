package com.tylerfitzgerald.demo_api.erc721.token.traits.creators;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import java.util.List;

public class MergeTokenWeightlessTraitsCreator extends AbstractWeightlessTraitsCreator {

  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;

  public AbstractWeightlessTraitsCreator setContextData(
      List<WeightedTraitDTO> weightedTraits,
      List<WeightedTraitTypeDTO> weightedTraitTypes,
      List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights,
      TokenFacadeDTO burnedNft1,
      TokenFacadeDTO burnedNft2
  ) {
    this.weightedTraits = weightedTraits;
    this.weightedTraitTypes = weightedTraitTypes;
    this.weightedTraitTypeWeights = weightedTraitTypeWeights;
    this.burnedNft1 = burnedNft1;
    this.burnedNft2 = burnedNft2;
    return this;
  }

  @Override
  protected String getWeightlessTraitValue(WeightlessTraitTypeDTO weightlessTraitType)
      throws WeightlessTraitException {
    int traitTypeId = Math.toIntExact(weightlessTraitType.getWeightlessTraitTypeId());
    switch (traitTypeId) {
      case WeightlessTraitTypeConstants.TILE_1_RARITY:
      case WeightlessTraitTypeConstants.TILE_2_RARITY:
      case WeightlessTraitTypeConstants.TILE_3_RARITY:
      case WeightlessTraitTypeConstants.TILE_4_RARITY:
        return mergeRarityTraitPicker.getValue(
            WeightlessTraitContext.builder()
                .traitTypeId(traitTypeId)
                .burnedNft1(burnedNft1)
                .burnedNft2(burnedNft2)
                .build());
      case WeightlessTraitTypeConstants.OVERALL_RARITY:
        return overallRarityTraitPicker.getValue(
            WeightlessTraitContext.builder()
                .seedForTrait(null)
                .weightedTraits(weightedTraits)
                .weightedTraitTypeWeights(weightedTraitTypeWeights)
                .weightlessTraits(getCreatedTraits())
                .build());
      case WeightlessTraitTypeConstants.TILE_1_EMOJI:
      case WeightlessTraitTypeConstants.TILE_2_EMOJI:
      case WeightlessTraitTypeConstants.TILE_3_EMOJI:
      case WeightlessTraitTypeConstants.TILE_4_EMOJI:
      case WeightlessTraitTypeConstants.TILE_1_COLOR:
      case WeightlessTraitTypeConstants.TILE_2_COLOR:
      case WeightlessTraitTypeConstants.TILE_3_COLOR:
      case WeightlessTraitTypeConstants.TILE_4_COLOR:
        return findWeightlessTraitValueFromListByType(burnedNft1, (long) traitTypeId);
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
