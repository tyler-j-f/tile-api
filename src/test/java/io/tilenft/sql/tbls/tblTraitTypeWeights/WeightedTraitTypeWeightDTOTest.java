package io.tilenft.sql.tbls.tblTraitTypeWeights;

import static org.assertj.core.api.Assertions.assertThat;

import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import org.junit.jupiter.api.Test;

public class WeightedTraitTypeWeightDTOTest {

  // Value set 1
  private static final Long ID = 1L;
  private static final Long TRAIT_TYPE_WEIGHT_ID = 2L;
  private static final Long TRAIT_TYPE_ID = 3L;
  private static final Long LIKELIHOOD = 4L;
  private static final String VALUE = "STRING_A";
  private static final String DISPLAY_TYPE_VALUE = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 5L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_2 = 6L;
  private static final Long TRAIT_TYPE_ID_2 = 7L;
  private static final Long LIKELIHOOD_2 = 8L;
  private static final String VALUE_2 = "STRING_C";
  private static final String DISPLAY_TYPE_VALUE_2 = "STRING_D";

  @Test
  void testConstructor() {
    // Create with value set 1.
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO =
        new WeightedTraitTypeWeightDTO(
            ID, TRAIT_TYPE_WEIGHT_ID, TRAIT_TYPE_ID, LIKELIHOOD, VALUE, DISPLAY_TYPE_VALUE);
    // Assert that getters return value set 1.
    assertThat(weightedTraitTypeWeightDTO.getId()).isEqualTo(ID);
    assertThat(weightedTraitTypeWeightDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
    assertThat(weightedTraitTypeWeightDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(weightedTraitTypeWeightDTO.getLikelihood()).isEqualTo(LIKELIHOOD);
    assertThat(weightedTraitTypeWeightDTO.getValue()).isEqualTo(VALUE);
    assertThat(weightedTraitTypeWeightDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE);
  }

  @Test
  void testGetterSetters() {
    // Create with value set 1 initially.
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO =
        new WeightedTraitTypeWeightDTO(
            ID, TRAIT_TYPE_WEIGHT_ID, TRAIT_TYPE_ID, LIKELIHOOD, VALUE, DISPLAY_TYPE_VALUE);
    // Set value set 2.
    weightedTraitTypeWeightDTO.setId(ID_2);
    weightedTraitTypeWeightDTO.setTraitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2);
    weightedTraitTypeWeightDTO.setTraitTypeId(TRAIT_TYPE_ID_2);
    weightedTraitTypeWeightDTO.setLikelihood(LIKELIHOOD_2);
    weightedTraitTypeWeightDTO.setValue(VALUE_2);
    weightedTraitTypeWeightDTO.setDisplayTypeValue(DISPLAY_TYPE_VALUE_2);
    // Assert that getters return value set 2.
    assertThat(weightedTraitTypeWeightDTO.getId()).isEqualTo(ID_2);
    assertThat(weightedTraitTypeWeightDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID_2);
    assertThat(weightedTraitTypeWeightDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID_2);
    assertThat(weightedTraitTypeWeightDTO.getLikelihood()).isEqualTo(LIKELIHOOD_2);
    assertThat(weightedTraitTypeWeightDTO.getValue()).isEqualTo(VALUE_2);
    assertThat(weightedTraitTypeWeightDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE_2);
  }

  @Test
  void testBuilder() {
    WeightedTraitTypeWeightDTO.WeightedTraitTypeWeightDTOBuilder builder =
        WeightedTraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE);
    assertThat(builder)
        .isInstanceOf(WeightedTraitTypeWeightDTO.WeightedTraitTypeWeightDTOBuilder.class);
    WeightedTraitTypeWeightDTO weightedTraitTypeWeightDTO = builder.build();
    assertThat(weightedTraitTypeWeightDTO).isInstanceOf(WeightedTraitTypeWeightDTO.class);
    // Assert that getters return value set 1.
    assertThat(weightedTraitTypeWeightDTO.getId()).isEqualTo(ID);
    assertThat(weightedTraitTypeWeightDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
    assertThat(weightedTraitTypeWeightDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(weightedTraitTypeWeightDTO.getLikelihood()).isEqualTo(LIKELIHOOD);
    assertThat(weightedTraitTypeWeightDTO.getValue()).isEqualTo(VALUE);
    assertThat(weightedTraitTypeWeightDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE);
  }
}
