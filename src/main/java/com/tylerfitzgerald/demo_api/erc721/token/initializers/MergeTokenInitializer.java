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
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.ArrayList;
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

  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
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
    weightedTraitTypes = filterOutWeightedTraitTypesToIgnore(weightedTraitTypeRepository.read());
    weightedTraitTypeWeights = weightedTraitTypeWeightRepository.read();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    weightedTraits = createWeightedTraits(seedForTraits);
    createWeightlessTraits(seedForTraits);
    TokenFacade token = tokenFacade.setTokenFacadeDTO(buildNFTFacade());
    return token.buildTokenDataDTO();
  }

  public WeightlessTraitDTO createWeightlessTrait(
      WeightlessTraitTypeDTO weightlessTraitType, Long seedForTrait)
      throws WeightlessTraitException {
    Long weightTraitId = weightlessTraitRepository.read().size() + 1L;
    String traitValue = getWeightlessTraitValue(weightlessTraitType);
    if (traitValue == null || traitValue.equals("")) {
      return null;
    }
    return weightlessTraitRepository.create(
        WeightlessTraitDTO.builder()
            .id(null)
            .traitId(weightTraitId)
            .tokenId(tokenDTO.getTokenId())
            .traitTypeId(weightlessTraitType.getWeightlessTraitTypeId())
            .value(traitValue)
            .displayTypeValue(getWeightlessTraitDisplayTypeValue())
            .build());
  }

  private String getWeightlessTraitValue(WeightlessTraitTypeDTO weightlessTraitType)
      throws WeightlessTraitException {
    Long traitTypeId = weightlessTraitType.getWeightlessTraitTypeId();
    if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_RARITY) {
      return getRarityValue(
          WeightlessTraitTypeConstants.TILE_1_RARITY, WeightedTraitTypeConstants.TILE_1_MULTIPLIER);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_2_RARITY) {
      return getRarityValue(
          WeightlessTraitTypeConstants.TILE_2_RARITY, WeightedTraitTypeConstants.TILE_2_MULTIPLIER);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_3_RARITY) {
      return getRarityValue(
          WeightlessTraitTypeConstants.TILE_3_RARITY, WeightedTraitTypeConstants.TILE_3_MULTIPLIER);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_4_RARITY) {
      return getRarityValue(
          WeightlessTraitTypeConstants.TILE_4_RARITY, WeightedTraitTypeConstants.TILE_4_MULTIPLIER);
    } else if (traitTypeId == WeightlessTraitTypeConstants.OVERALL_RARITY) {
      return rarityTraitPicker.getValue(
          WeightlessTraitContext.builder()
              .seedForTrait(null)
              .weightedTraits(weightedTraits)
              .weightedTraitTypeWeights(weightedTraitTypeWeights)
              .weightlessTraits(weightlessTraits)
              .build());
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_EMOJI) {
      return findWeightlessTraitValueFromListByType(
          burnedNft1, (long) WeightlessTraitTypeConstants.TILE_1_EMOJI);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_2_EMOJI) {
      return findWeightlessTraitValueFromListByType(
          burnedNft1, (long) WeightlessTraitTypeConstants.TILE_2_EMOJI);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_3_EMOJI) {
      return findWeightlessTraitValueFromListByType(
          burnedNft1, (long) WeightlessTraitTypeConstants.TILE_3_EMOJI);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_4_EMOJI) {
      return findWeightlessTraitValueFromListByType(
          burnedNft1, (long) WeightlessTraitTypeConstants.TILE_4_EMOJI);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_COLOR) {
      return findWeightlessTraitValueFromListByType(
          burnedNft1, (long) WeightlessTraitTypeConstants.TILE_1_COLOR);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_2_COLOR) {
      return findWeightlessTraitValueFromListByType(
          burnedNft1, (long) WeightlessTraitTypeConstants.TILE_2_COLOR);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_3_COLOR) {
      return findWeightlessTraitValueFromListByType(
          burnedNft1, (long) WeightlessTraitTypeConstants.TILE_3_COLOR);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_4_COLOR) {
      return findWeightlessTraitValueFromListByType(
          burnedNft1, (long) WeightlessTraitTypeConstants.TILE_4_COLOR);
    } else {
      System.out.println("ERROR: Invalid mergeWeightlessTraitValue. traitTypeId: " + traitTypeId);
      return "invalid mergeWeightlessTraitValue";
    }
  }

  private String getRarityValue(int traitTypeId, int multiplierTraitTypeId) {
    String burnedToken1Rarity, burnedToken2Rarity;
    String[] burnedTokenRarityValues = getBurnedTokenTraitValues((long) traitTypeId);
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

  private String[] getBurnedTokenTraitValues(Long traitTypeId) {
    String[] burnedTokenTraitValues = getBurnedTokenWeightlessTraitValues(traitTypeId);
    if (burnedTokenTraitValues[0] == null || burnedTokenTraitValues[1] == null) {
      burnedTokenTraitValues =
          getBurnedTokenWeightedTraitValues(
              traitTypeId, burnedTokenTraitValues[0], burnedTokenTraitValues[1]);
    }
    return burnedTokenTraitValues;
  }

  private String[] getBurnedTokenWeightedTraitValues(
      Long traitTypeId, String burnedWeightlessTrait1Value, String burnedWeightlessTrait2Value) {
    if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_RARITY) {
      burnedWeightlessTrait1Value =
          burnedWeightlessTrait1Value != null
              ? burnedWeightlessTrait1Value
              : findWeightedTraitValue(
                  burnedNft1.getWeightedTraits(),
                  burnedNft1.getWeightedTraitTypeWeights(),
                  (long) WeightedTraitTypeConstants.TILE_1_RARITY);
      burnedWeightlessTrait2Value =
          burnedWeightlessTrait2Value != null
              ? burnedWeightlessTrait2Value
              : findWeightedTraitValue(
                  burnedNft2.getWeightedTraits(),
                  burnedNft2.getWeightedTraitTypeWeights(),
                  (long) WeightedTraitTypeConstants.TILE_1_RARITY);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_2_RARITY) {
      burnedWeightlessTrait1Value =
          burnedWeightlessTrait1Value != null
              ? burnedWeightlessTrait1Value
              : findWeightedTraitValue(
                  burnedNft1.getWeightedTraits(),
                  burnedNft1.getWeightedTraitTypeWeights(),
                  (long) WeightedTraitTypeConstants.TILE_2_RARITY);
      burnedWeightlessTrait2Value =
          burnedWeightlessTrait2Value != null
              ? burnedWeightlessTrait2Value
              : findWeightedTraitValue(
                  burnedNft2.getWeightedTraits(),
                  burnedNft2.getWeightedTraitTypeWeights(),
                  (long) WeightedTraitTypeConstants.TILE_2_RARITY);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_3_RARITY) {
      burnedWeightlessTrait1Value =
          burnedWeightlessTrait1Value != null
              ? burnedWeightlessTrait1Value
              : findWeightedTraitValue(
                  burnedNft1.getWeightedTraits(),
                  burnedNft1.getWeightedTraitTypeWeights(),
                  (long) WeightedTraitTypeConstants.TILE_3_RARITY);
      burnedWeightlessTrait2Value =
          burnedWeightlessTrait2Value != null
              ? burnedWeightlessTrait2Value
              : findWeightedTraitValue(
                  burnedNft2.getWeightedTraits(),
                  burnedNft2.getWeightedTraitTypeWeights(),
                  (long) WeightedTraitTypeConstants.TILE_3_RARITY);
    } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_4_RARITY) {
      burnedWeightlessTrait1Value =
          burnedWeightlessTrait1Value != null
              ? burnedWeightlessTrait1Value
              : findWeightedTraitValue(
                  burnedNft1.getWeightedTraits(),
                  burnedNft1.getWeightedTraitTypeWeights(),
                  (long) WeightedTraitTypeConstants.TILE_4_RARITY);
      burnedWeightlessTrait2Value =
          burnedWeightlessTrait2Value != null
              ? burnedWeightlessTrait2Value
              : findWeightedTraitValue(
                  burnedNft2.getWeightedTraits(),
                  burnedNft2.getWeightedTraitTypeWeights(),
                  (long) WeightedTraitTypeConstants.TILE_4_RARITY);
    } else {
      System.out.println("ERROR, cannot find burned token weightless trait values");
      return null;
    }
    String[] returnValues = new String[2];
    returnValues[0] = burnedWeightlessTrait1Value;
    returnValues[1] = burnedWeightlessTrait2Value;
    return returnValues;
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
      Long traitTypeId) {
    WeightedTraitDTO foundTrait =
        weightedTraitListHelper.findFirstByTraitTypeId(weightedTraits, traitTypeId);
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

  private List<WeightedTraitTypeDTO> filterOutWeightedTraitTypesToIgnore(
      List<WeightedTraitTypeDTO> traitTypes) {
    return weightedTraitTypesFinder.findByIgnoringTraitTypeIdList(
        traitTypes, WEIGHTED_TRAIT_TYPES_TO_IGNORE);
  }
}
