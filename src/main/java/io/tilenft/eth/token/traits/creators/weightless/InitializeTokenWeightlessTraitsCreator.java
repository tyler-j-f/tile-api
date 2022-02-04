package io.tilenft.eth.token.traits.creators.weightless;

import io.tilenft.eth.token.initializers.SeedForTrait;
import io.tilenft.eth.token.initializers.TokenInitializeException;
import io.tilenft.eth.token.traits.weightless.WeightlessTraitTypeConstants;
import io.tilenft.eth.token.traits.weightless.pickers.ColorTraitPicker;
import io.tilenft.eth.token.traits.weightless.pickers.EmojiTraitPicker;
import io.tilenft.eth.token.traits.weightless.pickers.WeightlessTraitPickerContext;
import io.tilenft.eth.token.traits.weightless.pickers.WeightlessTraitPickerException;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import java.util.ArrayList;
import org.springframework.beans.factory.annotation.Autowired;

public class InitializeTokenWeightlessTraitsCreator extends AbstractWeightlessTraitsCreator {

  @Autowired private EmojiTraitPicker emojiTraitPicker;
  @Autowired private ColorTraitPicker colorTraitPicker;

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
              WeightlessTraitPickerContext.builder()
                  .seedForTrait(getSeedForTrait(context.getSeedForTraits(), weightlessTraitType))
                  .build());
        case WeightlessTraitTypeConstants.TILE_1_COLOR:
        case WeightlessTraitTypeConstants.TILE_2_COLOR:
        case WeightlessTraitTypeConstants.TILE_3_COLOR:
        case WeightlessTraitTypeConstants.TILE_4_COLOR:
          return colorTraitPicker.getValue(
              WeightlessTraitPickerContext.builder()
                  .seedForTrait(getSeedForTrait(context.getSeedForTraits(), weightlessTraitType))
                  .build());
        case WeightlessTraitTypeConstants.OVERALL_RARITY:
          return overallRarityTraitPicker.getValue(
              WeightlessTraitPickerContext.builder()
                  .seedForTrait(null)
                  .weightedTraits(context.getWeightedTraits())
                  .weightedTraitTypeWeights(context.getWeightedTraitTypeWeights())
                  .weightlessTraits(new ArrayList<>())
                  .build());
        default:
          return "invalid weightlessTraitValue";
      }
    } catch (WeightlessTraitPickerException e) {
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
