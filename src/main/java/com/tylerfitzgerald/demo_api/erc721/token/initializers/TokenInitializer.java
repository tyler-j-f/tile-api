package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.ColorTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenInitializer extends AbstractTokenInitializer {

  @Autowired private EmojiTraitPicker emojiTraitPicker;
  @Autowired private ColorTraitPicker colorTraitPicker;

  /**
   * For creating deterministic traits we increment the random seed value after creating a trait.
   * For some trait types, doing this will could potentially result in repeated values for the next
   * trait. Multiply bu this const value to introduce pseudo-randomness.
   */
  private static final int SEED_MULTIPLIER = 7;

  private static final int[] WEIGHTLESS_TRAIT_TYPES_TO_IGNORE = {
    WeightlessTraitTypeConstants.TILE_1_RARITY,
    WeightlessTraitTypeConstants.TILE_2_RARITY,
    WeightlessTraitTypeConstants.TILE_3_RARITY,
    WeightlessTraitTypeConstants.TILE_4_RARITY
  };

  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };

  public TokenFacadeDTO initialize(Long tokenId) throws TokenInitializeException {
    return initialize(tokenId, System.currentTimeMillis());
  }

  public TokenFacadeDTO initialize(Long tokenId, Long seedForTraits)
      throws TokenInitializeException {
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
    weightlessTraitTypes =
        filterOutWeightlessTraitTypesToIgnore(
            weightlessTraitTypeRepository.read(), WEIGHTLESS_TRAIT_TYPES_TO_IGNORE);
    weightedTraits = createWeightedTraits(seedForTraits);
    createWeightlessTraits(seedForTraits);
    return buildNFTFacade();
  }

  protected List<WeightlessTraitDTO> createWeightlessTraits(Long seedForTraits)
      throws TokenInitializeException {
    for (WeightlessTraitTypeDTO weightlessTraitType : weightlessTraitTypes) {
      // Increment the seed so that we use a unique random value for each trait
      WeightlessTraitDTO weightlessTraitDTO =
          createWeightlessTrait(weightlessTraitType, seedForTraits++);
      if (weightlessTraitDTO != null) {
        weightlessTraits.add(weightlessTraitDTO);
      }
    }
    return weightlessTraits;
  }

  protected WeightlessTraitDTO createWeightlessTrait(
      WeightlessTraitTypeDTO weightlessTraitType, Long seedForTrait)
      throws TokenInitializeException {
    Long weightTraitId = weightlessTraitRepository.read().size() + 1L;
    return weightlessTraitRepository.create(
        WeightlessTraitDTO.builder()
            .id(null)
            .traitId(weightTraitId)
            .tokenId(tokenDTO.getTokenId())
            .traitTypeId(weightlessTraitType.getWeightlessTraitTypeId())
            .value(getWeightlessTraitValue(weightlessTraitType, seedForTrait))
            .displayTypeValue(getWeightlessTraitDisplayTypeValue())
            .build());
  }

  private String getWeightlessTraitValue(
      WeightlessTraitTypeDTO weightlessTraitType, Long seedForTrait)
      throws TokenInitializeException {
    try {
      Long traitTypeId = weightlessTraitType.getWeightlessTraitTypeId();
      if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_EMOJI
          || traitTypeId == WeightlessTraitTypeConstants.TILE_2_EMOJI
          || traitTypeId == WeightlessTraitTypeConstants.TILE_3_EMOJI
          || traitTypeId == WeightlessTraitTypeConstants.TILE_4_EMOJI) {
        return emojiTraitPicker.getValue(
            WeightlessTraitContext.builder().seedForTrait(seedForTrait * SEED_MULTIPLIER).build());
      } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_COLOR
          || traitTypeId == WeightlessTraitTypeConstants.TILE_2_COLOR
          || traitTypeId == WeightlessTraitTypeConstants.TILE_3_COLOR
          || traitTypeId == WeightlessTraitTypeConstants.TILE_4_COLOR) {
        return colorTraitPicker.getValue(
            WeightlessTraitContext.builder().seedForTrait(seedForTrait * SEED_MULTIPLIER).build());
      } else if (traitTypeId == WeightlessTraitTypeConstants.OVERALL_RARITY) {
        return rarityTraitPicker.getValue(
            WeightlessTraitContext.builder()
                .seedForTrait(null)
                .weightedTraits(weightedTraits)
                .weightedTraitTypeWeights(weightedTraitTypeWeights)
                .weightlessTraits(new ArrayList<>())
                .build());
      } else {
        return "invalid weightlessTraitValue";
      }
    } catch (WeightlessTraitException e) {
      throw new TokenInitializeException(e.getMessage(), e.getCause());
    }
  }
}
