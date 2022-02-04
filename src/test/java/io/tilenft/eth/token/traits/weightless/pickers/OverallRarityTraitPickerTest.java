package io.tilenft.eth.token.traits.weightless.pickers;

import static org.assertj.core.api.Assertions.assertThat;

import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeConstants;
import io.tilenft.eth.token.traits.weightless.OverallRarityCalculator;
import io.tilenft.eth.token.traits.weightless.WeightlessTraitTypeConstants;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
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
public class OverallRarityTraitPickerTest {

  private Long TOKEN_ID = 1L;
  private Long WEIGHTED_TRAIT_ID_1 = 30L;
  private Long WEIGHTED_TRAIT_ID_2 = 31L;
  private Long WEIGHTED_TRAIT_ID_3 = 32L;
  private Long WEIGHTED_TRAIT_ID_4 = 33L;
  private Long WEIGHTED_TRAIT_TYPE_ID_1 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_1_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_2 = Long.valueOf(WeightedTraitTypeConstants.TILE_1_RARITY);
  private Long WEIGHTED_TRAIT_TYPE_ID_3 =
      Long.valueOf(WeightedTraitTypeConstants.TILE_2_MULTIPLIER);
  private Long WEIGHTED_TRAIT_TYPE_ID_4 = Long.valueOf(WeightedTraitTypeConstants.TILE_2_RARITY);
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1 = 50L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2 = 51L;
  private Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_3 = 52L;
  private Long WEIGHTED_LIKELIHOOD_1 = 25L;
  private Long WEIGHTED_LIKELIHOOD_2 = 40L;
  private Long WEIGHTED_LIKELIHOOD_3 = 35L;
  private String WEIGHTED_VALUE_1 = "5";
  private String WEIGHTED_VALUE_2 = "10";
  private String WEIGHTED_VALUE_3 = "15";
  private String WEIGHTED_DISPLAY_TYPE_VALUE_1 = "";
  private String WEIGHTED_DISPLAY_TYPE_VALUE_2 = "";
  private String WEIGHTED_DISPLAY_TYPE_VALUE_3 = "";
  private Long WEIGHTLESS_TRAIT_TYPE_ID_1 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_1_RARITY);
  private Long WEIGHTLESS_TRAIT_TYPE_ID_2 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_2_RARITY);
  private Long WEIGHTLESS_LIKELIHOOD_1 = 25L;
  private Long WEIGHTLESS_TRAIT_ID_1 = 60L;
  private Long WEIGHTLESS_TRAIT_ID_2 = 61L;
  private String WEIGHTLESS_VALUE_1 = "20";
  private String WEIGHTLESS_VALUE_2 = "25";
  private String WEIGHTLESS_DISPLAY_TYPE_VALUE_1 = "";
  private String WEIGHTLESS_DISPLAY_TYPE_VALUE_2 = "";
  private Long CALCULATE_RARITY_RESULT_1 = 70L;
  private Long CALCULATE_RARITY_RESULT_2 = 71L;

  private WeightlessTraitPickerContext context;
  @Mock private OverallRarityCalculator overallRarityCalculator;

  @InjectMocks
  private OverallRarityTraitPicker overallRarityTraitPicker = new OverallRarityTraitPicker();

  private final Long SEED_FOR_TRAITS =
      Long.valueOf(new Random(System.currentTimeMillis()).nextInt());
  private List<WeightedTraitDTO> weightedTraits = new ArrayList<>();;
  private List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();;
  private List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();

  @BeforeEach
  public void setup() {
    mockWeightedTraits();
    mockWeightedTraitTypeWeights();
    mockWeightlessTraits();
  }

  @Test
  public void testOverallRarityTraitPicker() throws WeightlessTraitPickerException {
    mockContext();
    mockRarityCalculator();
    String returnValue = overallRarityTraitPicker.getValue(context);
    Mockito.verify(overallRarityCalculator, Mockito.times(1))
        .calculateRarity(weightedTraits, weightedTraitTypeWeights);
    assertThat(returnValue).isEqualTo(String.valueOf(CALCULATE_RARITY_RESULT_1));
  }

  @Test
  public void testOverallRarityTraitPickerWithWeightlessTraits()
      throws WeightlessTraitPickerException {
    mockContext(true);
    mockRarityCalculator(true);
    String returnValue = overallRarityTraitPicker.getValue(context);
    Mockito.verify(overallRarityCalculator, Mockito.times(1))
        .calculateRarity(weightedTraits, weightedTraitTypeWeights, weightlessTraits);
    assertThat(returnValue).isEqualTo(String.valueOf(CALCULATE_RARITY_RESULT_2));
  }

  private void mockRarityCalculator() {
    mockRarityCalculator(false);
    return;
  }

  private void mockRarityCalculator(boolean shouldAddWeightlessTraitsToContext) {
    if (shouldAddWeightlessTraitsToContext) {
      Mockito.when(
              overallRarityCalculator.calculateRarity(
                  weightedTraits, weightedTraitTypeWeights, weightlessTraits))
          .thenReturn(CALCULATE_RARITY_RESULT_2);
    } else {
      Mockito.when(
              overallRarityCalculator.calculateRarity(weightedTraits, weightedTraitTypeWeights))
          .thenReturn(CALCULATE_RARITY_RESULT_1);
    }
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
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_4)
            .traitId(WEIGHTED_TRAIT_ID_4)
            .build());
    return;
  }

  private void mockWeightedTraitTypeWeights() {
    // Map type weight 1 to weighted trait 3
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_3)
            .likelihood(WEIGHTED_LIKELIHOOD_1)
            .value(WEIGHTED_VALUE_1)
            .displayTypeValue(WEIGHTED_DISPLAY_TYPE_VALUE_1)
            .build());
    // Map type weight 2 to weighted trait 2
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .likelihood(WEIGHTED_LIKELIHOOD_2)
            .value(WEIGHTED_VALUE_2)
            .displayTypeValue(WEIGHTED_DISPLAY_TYPE_VALUE_2)
            .build());
    // Map type weight 3 to weighted trait 1
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_3)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .likelihood(WEIGHTED_LIKELIHOOD_3)
            .value(WEIGHTED_VALUE_3)
            .displayTypeValue(WEIGHTED_DISPLAY_TYPE_VALUE_3)
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
            .displayTypeValue(WEIGHTLESS_DISPLAY_TYPE_VALUE_1)
            .build());
    weightlessTraits.add(
        WeightlessTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(WEIGHTLESS_TRAIT_ID_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .value(WEIGHTLESS_VALUE_2)
            .displayTypeValue(WEIGHTLESS_DISPLAY_TYPE_VALUE_2)
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
