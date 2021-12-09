package com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitInterface;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypeWeightsListFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitsListFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitsListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class MergeRarityTraitPicker implements WeightlessTraitInterface {

  @Autowired private WeightedTraitsListFinder weightedTraitListHelper;
  @Autowired protected WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;
  @Autowired private WeightlessTraitsListFinder weightlessTraitInListFinder;
  private int traitTypeId;
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;

  @Override
  public String getValue(WeightlessTraitContext context) throws WeightlessTraitException {
    traitTypeId = context.getTraitTypeId();
    burnedNft1 = context.getBurnedNft1();
    burnedNft2 = context.getBurnedNft2();
    return getRarityValue();
  }

  private String getRarityValue() throws WeightlessTraitException {
    int multiplierTraitTypeId = getMultiplierTraitTypeId();
    String burnedToken1Rarity, burnedToken2Rarity;
    String[] burnedTokenRarityValues = getBurnedTokenRarityTraitValues();
    burnedToken1Rarity = burnedTokenRarityValues[0];
    burnedToken2Rarity = burnedTokenRarityValues[1];
    String multiplier1 =
        findWeightedTraitValue(
            burnedNft1.getWeightedTraits(),
            burnedNft1.getWeightedTraitTypeWeights(),
            (long) multiplierTraitTypeId);
    String multiplier2 =
        findWeightedTraitValue(
            burnedNft2.getWeightedTraits(),
            burnedNft2.getWeightedTraitTypeWeights(),
            (long) multiplierTraitTypeId);
    String mergeMultiplier1 =
        findWeightedTraitValue(
            burnedNft1.getWeightedTraits(),
            burnedNft1.getWeightedTraitTypeWeights(),
            (long) WeightedTraitTypeConstants.MERGE_MULTIPLIER);
    String mergeMultiplier2 =
        findWeightedTraitValue(
            burnedNft2.getWeightedTraits(),
            burnedNft2.getWeightedTraitTypeWeights(),
            (long) WeightedTraitTypeConstants.MERGE_MULTIPLIER);
    return calculateSubTileRarity(
        burnedToken1Rarity,
        burnedToken2Rarity,
        multiplier1,
        multiplier2,
        mergeMultiplier1,
        mergeMultiplier2);
  }

  private int getMultiplierTraitTypeId() throws WeightlessTraitException {
    switch (traitTypeId) {
      case WeightlessTraitTypeConstants.TILE_1_RARITY:
        return WeightedTraitTypeConstants.TILE_1_MULTIPLIER;
      case WeightlessTraitTypeConstants.TILE_2_RARITY:
        return WeightedTraitTypeConstants.TILE_2_MULTIPLIER;
      case WeightlessTraitTypeConstants.TILE_3_RARITY:
        return WeightedTraitTypeConstants.TILE_3_MULTIPLIER;
      case WeightlessTraitTypeConstants.TILE_4_RARITY:
        return WeightedTraitTypeConstants.TILE_4_MULTIPLIER;
      default:
        throw new WeightlessTraitException("Unexpected value. traitTypeId: " + traitTypeId);
    }
  }

  private String findWeightedTraitValue(
      List<WeightedTraitDTO> weightedTraits,
      List<WeightedTraitTypeWeightDTO> traitWeights,
      Long weightedTraitTypeId) {
    WeightedTraitDTO foundTrait =
        weightedTraitListHelper.findFirstByTraitTypeId(weightedTraits, weightedTraitTypeId);
    return weightedTraitTypeWeightsListFinder
        .findFirstByTraitTypeId(traitWeights, foundTrait.getTraitTypeWeightId())
        .getValue();
  }

  private String calculateSubTileRarity(
      String tile1Rarity,
      String tile2Rarity,
      String tile1Multiplier,
      String tile2Multiplier,
      String tile1MergeMultiplier,
      String tile2MergeMultiplier) {
    return String.valueOf(
        (Long.parseLong(tile1Rarity)
                * Long.parseLong(tile1Multiplier)
                * Long.parseLong(tile2MergeMultiplier))
            + (Long.parseLong(tile2Rarity)
                * Long.parseLong(tile2Multiplier)
                * Long.parseLong(tile1MergeMultiplier)));
  }

  private String[] getBurnedTokenRarityTraitValues() throws WeightlessTraitException {
    String[] burnedTokenTraitValues =
        new String[] {
          findWeightlessTraitValueFromListByType(burnedNft1),
          findWeightlessTraitValueFromListByType(burnedNft2)
        };
    if (burnedTokenTraitValues[0] == null || burnedTokenTraitValues[1] == null) {
      burnedTokenTraitValues =
          getBurnedTokenWeightedTraitValues(burnedTokenTraitValues[0], burnedTokenTraitValues[1]);
    }
    return burnedTokenTraitValues;
  }

  private String[] getBurnedTokenWeightedTraitValues(
      String burnedWeightlessTrait1Value, String burnedWeightlessTrait2Value)
      throws WeightlessTraitException {
    int traitTypeIdInt = Math.toIntExact(traitTypeId);
    Long weightedTraitTypeId;
    switch (traitTypeIdInt) {
      case WeightlessTraitTypeConstants.TILE_1_RARITY:
        weightedTraitTypeId = (long) WeightedTraitTypeConstants.TILE_1_RARITY;
        break;
      case WeightlessTraitTypeConstants.TILE_2_RARITY:
        weightedTraitTypeId = (long) WeightedTraitTypeConstants.TILE_2_RARITY;
        break;
      case WeightlessTraitTypeConstants.TILE_3_RARITY:
        weightedTraitTypeId = (long) WeightedTraitTypeConstants.TILE_3_RARITY;
        break;
      case WeightlessTraitTypeConstants.TILE_4_RARITY:
        weightedTraitTypeId = (long) WeightedTraitTypeConstants.TILE_4_RARITY;
        break;
      default:
        throw new WeightlessTraitException("Unexpected value: " + traitTypeIdInt);
    }
    burnedWeightlessTrait1Value =
        burnedWeightlessTrait1Value != null
            ? burnedWeightlessTrait1Value
            : findWeightedTraitValue(
                burnedNft1.getWeightedTraits(),
                burnedNft1.getWeightedTraitTypeWeights(),
                weightedTraitTypeId);
    burnedWeightlessTrait2Value =
        burnedWeightlessTrait2Value != null
            ? burnedWeightlessTrait2Value
            : findWeightedTraitValue(
                burnedNft2.getWeightedTraits(),
                burnedNft2.getWeightedTraitTypeWeights(),
                weightedTraitTypeId);
    return new String[] {burnedWeightlessTrait1Value, burnedWeightlessTrait2Value};
  }

  private String findWeightlessTraitValueFromListByType(TokenFacadeDTO nft) {
    WeightlessTraitDTO weightedTrait =
        weightlessTraitInListFinder.findFirstByTraitTypeId(
            nft.getWeightlessTraits(), Long.valueOf(traitTypeId));
    if (weightedTrait != null) {
      return weightedTrait.getValue();
    }
    return null;
  }

  @Override
  public String getDisplayValue(WeightlessTraitContext context) throws WeightlessTraitException {
    return "";
  }
}
