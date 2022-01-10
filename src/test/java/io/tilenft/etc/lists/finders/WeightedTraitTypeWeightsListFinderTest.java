package io.tilenft.etc.lists.finders;

import static org.assertj.core.api.Assertions.assertThat;

import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeightedTraitTypeWeightsListFinderTest {

  private List<WeightedTraitTypeWeightDTO> weightedTraitTypesWeightsList;
  // Value set 1
  private static final Long ID_1 = 1L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_1 = 2L;
  private static final Long TRAIT_TYPE_ID_1 = 3L;
  private static final Long LIKELIHOOD_1 = 4L;
  private static final String VALUE_1 = "STRING_A";
  private static final String DISPLAY_TYPE_VALUE_1 = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 5L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_2 = 6L;
  private static final Long TRAIT_TYPE_ID_2 = 7L;
  private static final Long LIKELIHOOD_2 = 8L;
  private static final String VALUE_2 = "STRING_C";
  private static final String DISPLAY_TYPE_VALUE_2 = "STRING_D";
  // Value set 3
  private static final Long ID_3 = 9L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_3 = 10L;
  private static final Long TRAIT_TYPE_ID_3 = 11L;
  private static final Long LIKELIHOOD_3 = 12L;
  private static final String VALUE_3 = "STRING_E";
  private static final String DISPLAY_TYPE_VALUE_3 = "STRING_F";
  // Not in list values
  private static final Long TRAIT_TYPE_WEIGHT_ID_NOT_IN_LIST = 13L;

  @BeforeEach
  public void setup() {
    setupList();
  }

  private void setupList() {
    weightedTraitTypesWeightsList = new ArrayList<>();
    weightedTraitTypesWeightsList.add(
        WeightedTraitTypeWeightDTO.builder()
            .id(ID_1)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(TRAIT_TYPE_ID_1)
            .likelihood(LIKELIHOOD_1)
            .value(VALUE_1)
            .displayTypeValue(DISPLAY_TYPE_VALUE_1)
            .build());
    weightedTraitTypesWeightsList.add(
        WeightedTraitTypeWeightDTO.builder()
            .id(ID_2)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .likelihood(LIKELIHOOD_2)
            .value(VALUE_2)
            .displayTypeValue(DISPLAY_TYPE_VALUE_2)
            .build());
    weightedTraitTypesWeightsList.add(
        WeightedTraitTypeWeightDTO.builder()
            .id(ID_3)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_3)
            .traitTypeId(TRAIT_TYPE_ID_3)
            .likelihood(LIKELIHOOD_3)
            .value(VALUE_3)
            .displayTypeValue(DISPLAY_TYPE_VALUE_3)
            .build());
  }

  @Test
  void testFindFirstByTraitTypeId() {
    WeightedTraitTypeWeightsListFinder weightedTraitTypeWeightsListFinder =
        new WeightedTraitTypeWeightsListFinder();
    assertThat(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                weightedTraitTypesWeightsList, TRAIT_TYPE_WEIGHT_ID_1))
        .isNotNull();
    assertThat(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                weightedTraitTypesWeightsList, TRAIT_TYPE_WEIGHT_ID_2))
        .isNotNull();
    assertThat(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                weightedTraitTypesWeightsList, TRAIT_TYPE_WEIGHT_ID_3))
        .isNotNull();
    assertThat(
            weightedTraitTypeWeightsListFinder.findFirstByTraitTypeWeightId(
                weightedTraitTypesWeightsList, TRAIT_TYPE_WEIGHT_ID_NOT_IN_LIST))
        .isNull();
  }
}
