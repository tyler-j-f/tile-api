package com.tylerfitzgerald.demo_api.erc721.token.traits.creators;

import com.tylerfitzgerald.demo_api.erc721.token.initializers.SeedForTrait;
import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import java.util.ArrayList;
import java.util.List;

public class InitializeTokenWeightlessTraitsCreator extends AbstractWeightlessTraitsCreator {

  public AbstractWeightlessTraitsCreator setContextData(
      List<WeightedTraitDTO> weightedTraits,
      List<WeightedTraitTypeDTO> weightedTraitTypes,
      List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights
  ) {
    this.weightedTraits = weightedTraits;
    this.weightedTraitTypes = weightedTraitTypes;
    this.weightedTraitTypeWeights = weightedTraitTypeWeights;
    return this;
  }

  @Override
  protected String getWeightlessTraitValue(WeightlessTraitTypeDTO weightlessTraitType)
      throws TokenInitializeException {
    try {
      switch (Math.toIntExact(weightlessTraitType.getWeightlessTraitTypeId())) {
        case WeightlessTraitTypeConstants.TILE_1_EMOJI:
        case WeightlessTraitTypeConstants.TILE_2_EMOJI:
        case WeightlessTraitTypeConstants.TILE_3_EMOJI:
        case WeightlessTraitTypeConstants.TILE_4_EMOJI:
          return emojiTraitPicker.getValue(
              WeightlessTraitContext.builder()
                  .seedForTrait(getSeedForTrait(seedForTraits, weightlessTraitType))
                  .build());
        case WeightlessTraitTypeConstants.TILE_1_COLOR:
        case WeightlessTraitTypeConstants.TILE_2_COLOR:
        case WeightlessTraitTypeConstants.TILE_3_COLOR:
        case WeightlessTraitTypeConstants.TILE_4_COLOR:
          return colorTraitPicker.getValue(
              WeightlessTraitContext.builder()
                  .seedForTrait(getSeedForTrait(seedForTraits, weightlessTraitType))
                  .build());
        case WeightlessTraitTypeConstants.OVERALL_RARITY:
          return overallRarityTraitPicker.getValue(
              WeightlessTraitContext.builder()
                  .seedForTrait(null)
                  .weightedTraits(weightedTraits)
                  .weightedTraitTypeWeights(weightedTraitTypeWeights)
                  .weightlessTraits(new ArrayList<>())
                  .build());
        default:
          return "invalid weightlessTraitValue";
      }
    } catch (WeightlessTraitException e) {
      throw new TokenInitializeException(e.getMessage(), e.getCause());
    }
  }

  protected Long getSeedForTrait(Long seedForTraits, WeightlessTraitTypeDTO weightlessTraitType) {
    return (long)
        SeedForTrait.builder()
            .seedForTraits(seedForTraits)
            .weightlessTraitTypeDTO(weightlessTraitType)
            .build()
            .hashCode();
  }
}
