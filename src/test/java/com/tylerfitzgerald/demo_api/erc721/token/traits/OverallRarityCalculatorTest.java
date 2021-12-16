package com.tylerfitzgerald.demo_api.erc721.token.traits;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.erc721.token.traits.weightedTraits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.OverallRarityCalculator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.WeightlessTraitPickerContext;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypeWeightsListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class OverallRarityCalculatorTest {

  private Long TOKEN_ID = 1L;
  private Long WEIGHTED_TRAIT_ID_1 = 30L;
  private Long WEIGHTED_TRAIT_ID_2 = 31L;
  private Long WEIGHTED_TRAIT_ID_3 = 32L;
  private Long WEIGHTED_TRAIT_ID_4 = 33L;
  private Long WEIGHTED_TRAIT_ID_5 = 34L;
  private Long WEIGHTED_TRAIT_ID_6 = 35L;
  private Long WEIGHTED_TRAIT_ID_7 = 36L;
  private Long WEIGHTED_TRAIT_ID_8 = 37L;
  private Long WEIGHTED_TRAIT_TYPE_ID_1 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_1_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_2 = Long.valueOf(WeightedTraitTypeConstants.TILE_1_RARITY);
  private Long WEIGHTED_TRAIT_TYPE_ID_3 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_2_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_4 = Long.valueOf(WeightedTraitTypeConstants.TILE_2_RARITY);
  private Long WEIGHTED_TRAIT_TYPE_ID_5 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_3_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_6 = Long.valueOf(WeightedTraitTypeConstants.TILE_3_RARITY);
  private Long WEIGHTED_TRAIT_TYPE_ID_7 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_4_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_8 = Long.valueOf(WeightedTraitTypeConstants.TILE_4_RARITY);
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1 = 50L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2 = 51L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_3 = 52L;
  private Long WEIGHTED_LIKELIHOOD_1 = 19L;
  private Long WEIGHTED_LIKELIHOOD_2 = 40L;
  private Long WEIGHTED_LIKELIHOOD_3 = 41L;
  private String WEIGHTED_VALUE_1 = "2";
  private String WEIGHTED_VALUE_2 = "3";
  private String WEIGHTED_VALUE_3 = "5";
  private String WEIGHTED_DISPLAY_TYPE_VALUE = "";
  private Long WEIGHTLESS_TRAIT_TYPE_ID_1 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_1_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_2 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_2_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_3 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_3_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_4 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_4_RARITY);
  private Long WEIGHTLESS_TRAIT_ID_1 = 60L;
  private Long WEIGHTLESS_TRAIT_ID_2 = 61L;
  private Long WEIGHTLESS_TRAIT_ID_3 = 62L;
  private Long WEIGHTLESS_TRAIT_ID_4 = 63L;
  private String WEIGHTLESS_VALUE_1 = "7";
  private String WEIGHTLESS_VALUE_2 = "9";
  private String WEIGHTLESS_VALUE_3 = "11";
  private String WEIGHTLESS_VALUE_4 = "13";
  private String WEIGHTLESS_DISPLAY_TYPE_VALUE = "";
  private Long CALCULATE_RARITY_RESULT_1 = 32L;
  private Long CALCULATE_RARITY_RESULT_2 = 146L;
  private WeightlessTraitPickerContext context;
  @Mock private WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder;

  @InjectMocks
  private OverallRarityCalculator overallRarityCalculator = new OverallRarityCalculator();

  private final Long SEED_FOR_TRAITS =
      Long.valueOf(new Random(System.currentTimeMillis()).nextInt());
  private final int NUMBER_OF_TIMES_TO_REPEAT_TEST = 10;
  private List<WeightedTraitDTO> weightedTraits = new ArrayList<>();;
  private List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();;
  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();

  @BeforeEach
  public void setup() {
    mockWeightedTraits();
    mockWeightedTraitTypeWeights();
    mockWeightlessTraits();
    mockWeightedTraitTypeWeightsListFinder();
  }

  @Test
  public void testOverallRarityTraitPicker() {
    mockContext();
    Long returnValue =
        overallRarityCalculator.calculateRarity(weightedTraits, weightedTraitTypeWeights);
    Mockito.verify(weightedTraitTypeWeightsListFinder, Mockito.times(4))
        .findFirstByTraitTypeWeightId(weightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1);
    Mockito.verify(weightedTraitTypeWeightsListFinder, Mockito.times(2))
        .findFirstByTraitTypeWeightId(weightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2);
    Mockito.verify(weightedTraitTypeWeightsListFinder, Mockito.times(2))
        .findFirstByTraitTypeWeightId(weightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_WEIGHT_ID_3);
    assertThat(returnValue).isEqualTo(CALCULATE_RARITY_RESULT_1);
  }

  @Test
  public void testOverallRarityTraitPickerWithWeightlessTraits() {
    mockContext();
    Long returnValue =
        overallRarityCalculator.calculateRarity(
            weightedTraits, weightedTraitTypeWeights, weightlessTraits);
    Mockito.verify(weightedTraitTypeWeightsListFinder, Mockito.times(2))
        .findFirstByTraitTypeWeightId(weightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1);
    Mockito.verify(weightedTraitTypeWeightsListFinder, Mockito.times(2))
        .findFirstByTraitTypeWeightId(weightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_WEIGHT_ID_3);
    assertThat(returnValue).isEqualTo(CALCULATE_RARITY_RESULT_2);
  }

  private void mockWeightedTraitTypeWeightsListFinder() {
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                weightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1))
        .thenReturn(weightedTraitTypeWeights.get(0));
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                weightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2))
        .thenReturn(weightedTraitTypeWeights.get(1));
    Mockito.when(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                weightedTraitTypeWeights, WEIGHTED_TRAIT_TYPE_WEIGHT_ID_3))
        .thenReturn(weightedTraitTypeWeights.get(2));
  }

  private void mockWeightedTraits() {
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .traitId(WEIGHTED_TRAIT_ID_1)
            .build());
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .traitId(WEIGHTED_TRAIT_ID_2)
            .build());
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_3)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .traitId(WEIGHTED_TRAIT_ID_3)
            .build());
    // Re-use WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1. We only have 3 weights that we mocked so... may as
    // well
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .traitId(WEIGHTED_TRAIT_ID_4)
            .build());
    // Re-use the weighted trait type weights one more time each
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_5)
            .traitId(WEIGHTED_TRAIT_ID_5)
            .build());
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_6)
            .traitId(WEIGHTED_TRAIT_ID_6)
            .build());
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_3)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_7)
            .traitId(WEIGHTED_TRAIT_ID_7)
            .build());
    // Re-use WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1. We only have 3 weights that we mocked so... may as
    // well
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_8)
            .traitId(WEIGHTED_TRAIT_ID_8)
            .build());
    return;
  }

  private void mockWeightedTraitTypeWeights() {
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .likelihood(WEIGHTED_LIKELIHOOD_1)
            .value(WEIGHTED_VALUE_1)
            .displayTypeValue(WEIGHTED_DISPLAY_TYPE_VALUE)
            .build());
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .likelihood(WEIGHTED_LIKELIHOOD_2)
            .value(WEIGHTED_VALUE_2)
            .displayTypeValue(WEIGHTED_DISPLAY_TYPE_VALUE)
            .build());
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_3)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .likelihood(WEIGHTED_LIKELIHOOD_3)
            .value(WEIGHTED_VALUE_3)
            .displayTypeValue(WEIGHTED_DISPLAY_TYPE_VALUE)
            .build());
    return;
  }

  private void mockWeightlessTraits() {
    weightlessTraits.add(
        WeightlessTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(WEIGHTLESS_TRAIT_ID_1)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .value(WEIGHTLESS_VALUE_1)
            .displayTypeValue(WEIGHTLESS_DISPLAY_TYPE_VALUE)
            .build());
    weightlessTraits.add(
        WeightlessTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(WEIGHTLESS_TRAIT_ID_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .value(WEIGHTLESS_VALUE_2)
            .displayTypeValue(WEIGHTLESS_DISPLAY_TYPE_VALUE)
            .build());
    weightlessTraits.add(
        WeightlessTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(WEIGHTLESS_TRAIT_ID_3)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_3)
            .value(WEIGHTLESS_VALUE_3)
            .displayTypeValue(WEIGHTLESS_DISPLAY_TYPE_VALUE)
            .build());
    weightlessTraits.add(
        WeightlessTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(WEIGHTLESS_TRAIT_ID_4)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_4)
            .value(WEIGHTLESS_VALUE_4)
            .displayTypeValue(WEIGHTLESS_DISPLAY_TYPE_VALUE)
            .build());
    return;
  }

  private void mockContext() {
    mockContext(false);
    return;
  }

  private void mockContext(boolean shouldAddWeightlessTraitsToContext) {
    WeightlessTraitPickerContext.WeightlessTraitPickerContextBuilder builder =
        WeightlessTraitPickerContext.builder();
    List<WeightlessTraitDTO> weightlessTraitsToSet =
        shouldAddWeightlessTraitsToContext ? weightlessTraits : new ArrayList<>();
    context =
        builder
            .seedForTrait(SEED_FOR_TRAITS)
            .weightedTraits(weightedTraits)
            .weightedTraitTypeWeights(weightedTraitTypeWeights)
            .weightlessTraits(weightlessTraitsToSet)
            .build();
    return;
  }
}
