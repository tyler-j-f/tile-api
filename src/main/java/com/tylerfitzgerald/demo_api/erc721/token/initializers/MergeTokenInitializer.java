package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenDataDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.MergeRarityTraitPicker;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightlessTraitsFinder;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import org.springframework.beans.factory.annotation.Autowired;

public class MergeTokenInitializer extends AbstractTokenInitializer {

  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.TILE_1_RARITY,
    WeightedTraitTypeConstants.TILE_2_RARITY,
    WeightedTraitTypeConstants.TILE_3_RARITY,
    WeightedTraitTypeConstants.TILE_4_RARITY,
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };

  @Autowired private WeightlessTraitsFinder weightlessTraitInListFinder;
  @Autowired private TokenFacade tokenFacade;
  @Autowired private MergeRarityTraitPicker mergeRarityTraitPicker;
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

  private String findWeightlessTraitValueFromListByType(TokenFacadeDTO nft, Long traitTypeId) {
    WeightlessTraitDTO weightedTrait =
        weightlessTraitInListFinder.findFirstByTraitTypeId(nft.getWeightlessTraits(), traitTypeId);
    if (weightedTrait != null) {
      return weightedTrait.getValue();
    }
    return null;
  }
}
