package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenDataDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightedTraitsFinder;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightlessTraitsFinder;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class MergeTokenInitializer extends AbstractTokenInitializer {

  @Autowired private WeightlessTraitsFinder weightlessTraitInListFinder;
  @Autowired private WeightedTraitsFinder weightedTraitListHelper;
  @Autowired private TokenFacade tokenFacade;

  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.TILE_1_RARITY,
    WeightedTraitTypeConstants.TILE_2_RARITY,
    WeightedTraitTypeConstants.TILE_3_RARITY,
    WeightedTraitTypeConstants.TILE_4_RARITY,
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };

  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;

  public TokenDataDTO initialize(
      Long tokenId, TokenFacadeDTO burnedNft1, TokenFacadeDTO burnedNft2, Long seedForTraits)
      throws TokenInitializeException, WeightlessTraitException {
    this.burnedNft1 = burnedNft1;
    this.burnedNft2 = burnedNft2;
    if (burnedNft1 == null) {
      System.out.println(
          "TokenInitializer failed to load burned token 1. burnedNft1: " + burnedNft1);
      return null;
    }
    if (burnedNft2 == null) {
      System.out.println(
          "TokenInitializer failed to load burned token 2. burnedNft2: " + burnedNft2);
      return null;
    }
    tokenDTO = createToken(tokenId);
    if (tokenDTO == null) {
      System.out.println(
          "TokenInitializer failed to initialize the token with tokenId: " + tokenId);
      return null;
    }
    weightedTraitTypes =
        filterOutWeightedTraitTypesToIgnore(
            weightedTraitTypeRepository.read(), WEIGHTED_TRAIT_TYPES_TO_IGNORE);
    weightedTraitTypeWeights = weightedTraitTypeWeightRepository.read();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    weightedTraits = createWeightedTraits(seedForTraits);
    createWeightlessTraits(seedForTraits);
    TokenFacade token = tokenFacade.setTokenFacadeDTO(buildNFTFacade());
    return token.buildTokenDataDTO();
  }

  protected String getWeightlessTraitValue(
      WeightlessTraitTypeDTO weightlessTraitType, Long seedForTrait)
      throws WeightlessTraitException {
    int traitTypeId = Math.toIntExact(weightlessTraitType.getWeightlessTraitTypeId());
    switch (traitTypeId) {
      case WeightlessTraitTypeConstants.TILE_1_RARITY:
      case WeightlessTraitTypeConstants.TILE_2_RARITY:
      case WeightlessTraitTypeConstants.TILE_3_RARITY:
      case WeightlessTraitTypeConstants.TILE_4_RARITY:
        return getRarityValue(traitTypeId);
      case WeightlessTraitTypeConstants.OVERALL_RARITY:
        return rarityTraitPicker.getValue(
            WeightlessTraitContext.builder()
                .seedForTrait(null)
                .weightedTraits(weightedTraits)
                .weightedTraitTypeWeights(weightedTraitTypeWeights)
                .weightlessTraits(weightlessTraits)
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

  private int getMultiplierTraitTypeId(int traitTypeId) throws WeightlessTraitException {
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

  private String getRarityValue(int traitTypeId) throws WeightlessTraitException {
    int multiplierTraitTypeId = getMultiplierTraitTypeId(traitTypeId);
    String burnedToken1Rarity, burnedToken2Rarity;
    String[] burnedTokenRarityValues = getBurnedTokenRarityTraitValues((long) traitTypeId);
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

  private String[] getBurnedTokenWeightlessTraitValues(Long traitTypeId) {
    return new String[] {
      findWeightlessTraitValueFromListByType(burnedNft1, traitTypeId),
      findWeightlessTraitValueFromListByType(burnedNft2, traitTypeId)
    };
  }

  private String[] getBurnedTokenRarityTraitValues(Long traitTypeId)
      throws WeightlessTraitException {
    String[] burnedTokenTraitValues = getBurnedTokenWeightlessTraitValues(traitTypeId);
    if (burnedTokenTraitValues[0] == null || burnedTokenTraitValues[1] == null) {
      burnedTokenTraitValues =
          getBurnedTokenWeightedTraitValues(
              traitTypeId, burnedTokenTraitValues[0], burnedTokenTraitValues[1]);
    }
    return burnedTokenTraitValues;
  }

  private String[] getBurnedTokenWeightedTraitValues(
      Long traitTypeId, String burnedWeightlessTrait1Value, String burnedWeightlessTrait2Value)
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

  private String findWeightlessTraitValueFromListByType(TokenFacadeDTO nft, Long traitTypeId) {
    WeightlessTraitDTO weightedTrait =
        weightlessTraitInListFinder.findFirstByTraitTypeId(nft.getWeightlessTraits(), traitTypeId);
    if (weightedTrait != null) {
      return weightedTrait.getValue();
    }
    return null;
  }

  private String findWeightedTraitValue(
      List<WeightedTraitDTO> weightedTraits,
      List<WeightedTraitTypeWeightDTO> traitWeights,
      Long weightedTraitTypeId) {
    WeightedTraitDTO foundTrait =
        weightedTraitListHelper.findFirstByTraitTypeId(weightedTraits, weightedTraitTypeId);
    return weightedTraitTypeWeightsFinder
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
}
