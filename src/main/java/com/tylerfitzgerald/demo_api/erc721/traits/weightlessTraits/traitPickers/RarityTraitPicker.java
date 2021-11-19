package com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.traitPickers;

import com.tylerfitzgerald.demo_api.erc721.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitContext;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.traits.weightlessTraits.WeightlessTraitInterface;
import com.tylerfitzgerald.demo_api.image.ImageResourcesLoader;
import com.tylerfitzgerald.demo_api.sql.tblTraits.TraitDTO;
import java.io.IOException;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class RarityTraitPicker implements WeightlessTraitInterface {

  private Long tile1Value = 1L;
  private Long tile2Value = 1L;
  private Long tile3Value = 1L;
  private Long tile4Value = 1L;
  private Long tile1Multiplier = 1L;
  private Long tile2Multiplier = 1L;
  private Long tile3Multiplier = 1L;
  private Long tile4Multiplier = 1L;

  @Override
  public String getValue(WeightlessTraitContext context) throws WeightlessTraitException {
    for (TraitDTO weightedTraits : context.getWeightedTraits()) {
      switch (Math.toIntExact(weightedTraits.getTraitTypeId())) {
        case WeightedTraitTypeConstants.TILE_1_RARITY:
          System.out.println("Setting tile 1 value");
          tile1Value = 1L;
          break;
        case WeightedTraitTypeConstants.TILE_1_MULTIPLIER:
          System.out.println("Setting tile 1 mult");
          tile1Multiplier = 1L;
          break;
        default:
          break;
          // throw new IllegalStateException("Unexpected value: " +
          // weightedTraits.getTraitTypeId());
      }
    }
    return calculateTotalRarity().toString();
  }

  private Long calculateTotalRarity() {
    return (tile1Value * tile1Multiplier)
        + (tile2Value * tile2Multiplier)
        + (tile3Value * tile3Multiplier)
        + (tile4Value * tile4Multiplier);
  }

  @Override
  public String getDisplayValue(Long seedForTrait) throws WeightlessTraitException {
    return "";
  }
}
