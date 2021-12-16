package io.tilenft.erc721.token.traits.weightlessTraits.traitPickers;

import io.tilenft.erc721.token.TokenFacadeDTO;
import io.tilenft.erc721.token.traits.weightedTraits.WeightedTraitTypeConstants;
import io.tilenft.erc721.token.traits.weightlessTraits.WeightlessTraitTypeConstants;
import io.tilenft.etc.lsitFinders.WeightedTraitTypeWeightsListFinder;
import io.tilenft.etc.lsitFinders.WeightedTraitsListFinder;
import io.tilenft.etc.lsitFinders.WeightlessTraitsListFinder;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class MergeRarityTraitPicker implements WeightlessTraitPickerInterface {

  @Autowired private WeightedTraitsListFinder weightedTraitListHelper;
  @Autowired protected WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;
  @Autowired private WeightlessTraitsListFinder weightlessTraitInListFinder;
  private int traitTypeIdToCreate;
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;

  @Override
  public String getValue(WeightlessTraitPickerContext context)
      throws WeightlessTraitPickerException {
    traitTypeIdToCreate = context.getTraitTypeId();
    burnedNft1 = context.getBurnedNft1();
    burnedNft2 = context.getBurnedNft2();
    return getRarityValue();
  }

  private String getRarityValue() throws WeightlessTraitPickerException {
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

  private int getMultiplierTraitTypeId() throws WeightlessTraitPickerException {
    switch (traitTypeIdToCreate) {
      case WeightlessTraitTypeConstants.TILE_1_RARITY:
        return WeightedTraitTypeConstants.TILE_1_MULTIPLIER;
      case WeightlessTraitTypeConstants.TILE_2_RARITY:
        return WeightedTraitTypeConstants.TILE_2_MULTIPLIER;
      case WeightlessTraitTypeConstants.TILE_3_RARITY:
        return WeightedTraitTypeConstants.TILE_3_MULTIPLIER;
      case WeightlessTraitTypeConstants.TILE_4_RARITY:
        return WeightedTraitTypeConstants.TILE_4_MULTIPLIER;
      default:
        throw new WeightlessTraitPickerException(
            "Unexpected value. traitTypeId: " + traitTypeIdToCreate);
    }
  }

  private String findWeightedTraitValue(
      List<WeightedTraitDTO> weightedTraits,
      List<WeightedTraitTypeWeightDTO> traitWeights,
      Long weightedTraitTypeId) {
    WeightedTraitDTO foundTrait =
        weightedTraitListHelper.findFirstByTraitTypeId(weightedTraits, weightedTraitTypeId);
    return weightedTraitTypeWeightsListFinder
        .findFirstByTraitTypeWeightId(traitWeights, foundTrait.getTraitTypeWeightId())
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

  private String[] getBurnedTokenRarityTraitValues() throws WeightlessTraitPickerException {
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
      throws WeightlessTraitPickerException {
    int traitTypeIdInt = Math.toIntExact(traitTypeIdToCreate);
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
        throw new WeightlessTraitPickerException("Unexpected value: " + traitTypeIdInt);
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
            nft.getWeightlessTraits(), Long.valueOf(traitTypeIdToCreate));
    if (weightedTrait != null) {
      return weightedTrait.getValue();
    }
    return null;
  }

  @Override
  public String getDisplayValue(WeightlessTraitPickerContext context)
      throws WeightlessTraitPickerException {
    return "";
  }
}
