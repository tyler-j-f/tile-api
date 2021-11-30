package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers;

import com.tylerfitzgerald.demo_api.erc721.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.sql.tblTraitTypeWeights.TraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits.WeightlessTraitDTO;
import java.util.List;

public class RarityCalculator {
  private Long tile1Value = 1L;
  private Long tile2Value = 1L;
  private Long tile3Value = 1L;
  private Long tile4Value = 1L;
  private Long tile1Multiplier = 1L;
  private Long tile2Multiplier = 1L;
  private Long tile3Multiplier = 1L;
  private Long tile4Multiplier = 1L;

  public Long calculateRarity(
      List<TraitDTO> weightedTraits, List<TraitTypeWeightDTO> traitTypeWeights) {
    Long traitTypeWeightId;
    for (TraitDTO weightedTrait : weightedTraits) {
      traitTypeWeightId = weightedTrait.getTraitTypeWeightId();
      switch (Math.toIntExact(weightedTrait.getTraitTypeId())) {
        case WeightedTraitTypeConstants.TILE_1_RARITY:
          tile1Value = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_2_RARITY:
          tile2Value = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_3_RARITY:
          tile3Value = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_4_RARITY:
          tile4Value = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_1_MULTIPLIER:
          tile1Multiplier = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_2_MULTIPLIER:
          tile2Multiplier = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_3_MULTIPLIER:
          tile3Multiplier = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_4_MULTIPLIER:
          tile4Multiplier = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        default:
          // There will be weighted traits that are not one of the trait types that correspond to
          // rarity values, break; when we get to one of these.
          break;
      }
    }
    return calculate();
  }

  public Long calculateRarity(
      List<TraitDTO> weightedTraits,
      List<TraitTypeWeightDTO> traitTypeWeights,
      List<WeightlessTraitDTO> weightlessTraits) {
    Long traitTypeWeightId;
    for (TraitDTO weightedTrait : weightedTraits) {
      traitTypeWeightId = weightedTrait.getTraitTypeWeightId();
      switch (Math.toIntExact(weightedTrait.getTraitTypeId())) {
        case WeightedTraitTypeConstants.TILE_1_MULTIPLIER:
          tile1Multiplier = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_2_MULTIPLIER:
          tile2Multiplier = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_3_MULTIPLIER:
          tile3Multiplier = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        case WeightedTraitTypeConstants.TILE_4_MULTIPLIER:
          tile4Multiplier = getValueFromTraitWeightsList(traitTypeWeightId, traitTypeWeights);
          break;
        default:
          // There will be weighted traits that are not one of the trait types that correspond to
          // rarity values, break; when we get to one of these.
          break;
      }
    }
    for (WeightlessTraitDTO weightlessTrait : weightlessTraits) {
      switch (Math.toIntExact(weightlessTrait.getTraitTypeId())) {
        case WeightlessTraitTypeConstants.TILE_1_RARITY:
          tile1Value = Long.valueOf(weightlessTrait.getValue());
          break;
        case WeightlessTraitTypeConstants.TILE_2_RARITY:
          tile2Value = Long.valueOf(weightlessTrait.getValue());
          break;
        case WeightlessTraitTypeConstants.TILE_3_RARITY:
          tile3Value = Long.valueOf(weightlessTrait.getValue());
          break;
        case WeightlessTraitTypeConstants.TILE_4_RARITY:
          tile4Value = Long.valueOf(weightlessTrait.getValue());
          break;
        default:
          // There will be weighted traits that are not one of the trait types that correspond to
          // rarity values, break; when we get to one of these.
          break;
      }
    }
    return calculate();
  }

  private Long getValueFromTraitWeightsList(
      Long traitTypeWeightId, List<TraitTypeWeightDTO> traitTypeWeights) {
    return Long.valueOf(
        traitTypeWeights.stream()
            .filter(typeWeight -> typeWeight.getTraitTypeWeightId().equals(traitTypeWeightId))
            .findFirst()
            .get()
            .getValue());
  }

  private Long calculate() {
    return (tile1Value * tile1Multiplier)
        + (tile2Value * tile2Multiplier)
        + (tile3Value * tile3Multiplier)
        + (tile4Value * tile4Multiplier);
  }
}
