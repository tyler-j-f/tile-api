package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.ColorTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.listUtils.finders.WeightlessTraitTypesFinder;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypes.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenInitializer extends AbstractTokenInitializer {

  @Autowired private EmojiTraitPicker emojiTraitPicker;
  @Autowired private ColorTraitPicker colorTraitPicker;
  @Autowired private WeightlessTraitTypesFinder weightlessTraitTypesFinder;

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
    weightedTraitTypes = filterOutWeightedTraitTypesToIgnore(weightedTraitTypeRepository.read());
    weightedTraitTypeWeights = weightedTraitTypeWeightRepository.read();
    weightlessTraitTypes =
        filterOutWeightlessTraitTypesToIgnore(weightlessTraitTypeRepository.read());
    weightedTraits = createWeightedTraits(seedForTraits);
    createWeightlessTraits(seedForTraits);
    return buildNFTFacade();
  }

  private List<WeightlessTraitDTO> createWeightlessTraits(Long seedForTraits)
      throws TokenInitializeException {
    List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
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

  private WeightlessTraitDTO createWeightlessTrait(
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
            .displayTypeValue(getWeightlessTraitDisplayTypeValue(weightlessTraitType, seedForTrait))
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

  private String getWeightlessTraitDisplayTypeValue(
      WeightlessTraitTypeDTO weightlessTraitType, Long seedForTrait)
      throws TokenInitializeException {
    try {
      Long traitTypeId = weightlessTraitType.getWeightlessTraitTypeId();
      if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_EMOJI
          || traitTypeId == WeightlessTraitTypeConstants.TILE_2_EMOJI
          || traitTypeId == WeightlessTraitTypeConstants.TILE_3_EMOJI
          || traitTypeId == WeightlessTraitTypeConstants.TILE_4_EMOJI) {
        return emojiTraitPicker.getDisplayValue(null);
      } else if (traitTypeId == WeightlessTraitTypeConstants.TILE_1_COLOR
          || traitTypeId == WeightlessTraitTypeConstants.TILE_2_COLOR
          || traitTypeId == WeightlessTraitTypeConstants.TILE_3_COLOR
          || traitTypeId == WeightlessTraitTypeConstants.TILE_4_COLOR) {
        return colorTraitPicker.getDisplayValue(null);
      } else if (traitTypeId == WeightlessTraitTypeConstants.OVERALL_RARITY) {
        return rarityTraitPicker.getDisplayValue(null);
      } else {
        return "invalid weightlessTraitDisplayTypeValue";
      }
    } catch (WeightlessTraitException e) {
      throw new TokenInitializeException(e.getMessage(), e.getCause());
    }
  }

  private List<WeightedTraitDTO> createWeightedTraits(Long seedForTraits) {
    List<WeightedTraitDTO> traits = new ArrayList<>();
    for (WeightedTraitTypeDTO type : weightedTraitTypes) {
      // Increment the seed so that we use a unique random value for each trait
      WeightedTraitDTO trait = createWeightedTrait(type, seedForTraits++);
      if (trait != null) {
        traits.add(trait);
      }
    }
    return traits;
  }

  private WeightedTraitDTO createWeightedTrait(WeightedTraitTypeDTO type, Long seedForTrait) {
    Long traitTypeId = type.getTraitTypeId();
    List<WeightedTraitTypeWeightDTO> weights = getTraitTypeWeightsForTraitTypeId(traitTypeId);
    WeightedTraitTypeWeightDTO traitTypeWeight =
        getRandomTraitTypeWeightFromList(weights, seedForTrait);
    Long traitId = weightedTraitRepository.read().size() + 1L;
    return weightedTraitRepository.create(
        WeightedTraitDTO.builder()
            .id(null)
            .traitId(traitId)
            .tokenId(tokenDTO.getTokenId())
            .traitTypeId(traitTypeId)
            .traitTypeWeightId(traitTypeWeight.getTraitTypeWeightId())
            .build());
  }

  private List<WeightedTraitTypeWeightDTO> getTraitTypeWeightsForTraitTypeId(Long traitTypeId) {
    return weightedTraitTypeWeightsFinder.findByTraitTypeId(weightedTraitTypeWeights, traitTypeId);
  }

  private List<WeightlessTraitTypeDTO> filterOutWeightlessTraitTypesToIgnore(
      List<WeightlessTraitTypeDTO> weightlessTraitTypes) {
    return weightlessTraitTypesFinder.findByIgnoringTraitTypeIdList(
        weightlessTraitTypes, WEIGHTLESS_TRAIT_TYPES_TO_IGNORE);
  }

  private List<WeightedTraitTypeDTO> filterOutWeightedTraitTypesToIgnore(
      List<WeightedTraitTypeDTO> weightedTraitTypes) {
    return weightedTraitTypesFinder.findByIgnoringTraitTypeIdList(
        weightedTraitTypes, WEIGHTED_TRAIT_TYPES_TO_IGNORE);
  }

  private WeightedTraitTypeWeightDTO getRandomTraitTypeWeightFromList(
      List<WeightedTraitTypeWeightDTO> traitTypeWeights, Long randomNumberSeed) {
    int randomNumber = new Random(randomNumberSeed).nextInt(1, 100);
    Long count = 0L;
    for (WeightedTraitTypeWeightDTO traitTypeWeight : traitTypeWeights) {
      count = count + traitTypeWeight.getLikelihood();
      if (count >= randomNumber) {
        return traitTypeWeight;
      }
    }
    /**
     * We should never return null. If we get here: DB values for trait type weights (for a
     * particular trait type) are misconfigured.
     */
    return null;
  }
}
