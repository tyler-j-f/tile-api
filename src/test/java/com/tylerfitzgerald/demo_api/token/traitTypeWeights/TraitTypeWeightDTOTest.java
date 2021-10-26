package com.tylerfitzgerald.demo_api.token.traitTypeWeights;

import com.tylerfitzgerald.demo_api.token.traitTypes.TraitTypeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TraitTypeWeightDTOTest {

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
    TraitTypeWeightDTO traitTypeWeightDTO =
        new TraitTypeWeightDTO(
            ID, TRAIT_TYPE_WEIGHT_ID, TRAIT_TYPE_ID, LIKELIHOOD, VALUE, DISPLAY_TYPE_VALUE);
    // Assert that getters return value set 1.
    assertThat(traitTypeWeightDTO.getId()).isEqualTo(ID);
    assertThat(traitTypeWeightDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
    assertThat(traitTypeWeightDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(traitTypeWeightDTO.getLikelihood()).isEqualTo(LIKELIHOOD);
    assertThat(traitTypeWeightDTO.getValue()).isEqualTo(VALUE);
    assertThat(traitTypeWeightDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE);
  }

  @Test
  void testGetterSetters() {
    // Create with value set 1 initially.
    TraitTypeWeightDTO traitTypeWeightDTO =
        new TraitTypeWeightDTO(
            ID, TRAIT_TYPE_WEIGHT_ID, TRAIT_TYPE_ID, LIKELIHOOD, VALUE, DISPLAY_TYPE_VALUE);
    // Set value set 2.
    traitTypeWeightDTO.setId(ID_2);
    traitTypeWeightDTO.setTraitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2);
    traitTypeWeightDTO.setTraitTypeId(TRAIT_TYPE_ID_2);
    traitTypeWeightDTO.setLikelihood(LIKELIHOOD_2);
    traitTypeWeightDTO.setValue(VALUE_2);
    traitTypeWeightDTO.setDisplayTypeValue(DISPLAY_TYPE_VALUE_2);
    // Assert that getters return value set 2.
    assertThat(traitTypeWeightDTO.getId()).isEqualTo(ID_2);
    assertThat(traitTypeWeightDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID_2);
    assertThat(traitTypeWeightDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID_2);
    assertThat(traitTypeWeightDTO.getLikelihood()).isEqualTo(LIKELIHOOD_2);
    assertThat(traitTypeWeightDTO.getValue()).isEqualTo(VALUE_2);
    assertThat(traitTypeWeightDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE_2);
  }

  @Test
  void testBuilder() {
    TraitTypeWeightDTO.TraitTypeWeightDTOBuilder builder =
        TraitTypeWeightDTO.builder()
            .id(ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .likelihood(LIKELIHOOD)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE);
    assertThat(builder).isInstanceOf(TraitTypeWeightDTO.TraitTypeWeightDTOBuilder.class);
    TraitTypeWeightDTO traitTypeWeightDTO = builder.build();
    assertThat(traitTypeWeightDTO).isInstanceOf(TraitTypeWeightDTO.class);
    // Assert that getters return value set 1.
    assertThat(traitTypeWeightDTO.getId()).isEqualTo(ID);
    assertThat(traitTypeWeightDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
    assertThat(traitTypeWeightDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(traitTypeWeightDTO.getLikelihood()).isEqualTo(LIKELIHOOD);
    assertThat(traitTypeWeightDTO.getValue()).isEqualTo(VALUE);
    assertThat(traitTypeWeightDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE);
  }
}
