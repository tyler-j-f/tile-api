package com.tylerfitzgerald.demo_api.sql.tblWeightlessTraits;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeightlessWeightedTraitDTOTest {

  // Value set 1
  private static final Long ID = 1L;
  private static final Long WEIGHTLESS_TRAIT_ID = 2L;
  private static final Long TOKEN_ID = 3L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID = 4L;
  private static final String VALUE = "STRING_A";
  private static final String DISPLAY_TYPE_VALUE = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 5L;
  private static final Long WEIGHTLESS_TRAIT_ID_2 = 6L;
  private static final Long TOKEN_ID_2 = 7L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_2 = 8L;
  private static final String VALUE_2 = "STRING_C";
  private static final String DISPLAY_TYPE_VALUE_2 = "STRING_D";

  @Test
  void testConstructor() {
    // Create with value set 1.
    WeightlessTraitDTO weightlessTraitDTO =
        new WeightlessTraitDTO(
            ID, WEIGHTLESS_TRAIT_ID, TOKEN_ID, WEIGHTLESS_TRAIT_TYPE_ID, VALUE, DISPLAY_TYPE_VALUE);
    // Assert that getters return value set 1.
    assertThat(weightlessTraitDTO.getId()).isEqualTo(ID);
    assertThat(weightlessTraitDTO.getTraitId()).isEqualTo(WEIGHTLESS_TRAIT_ID);
    assertThat(weightlessTraitDTO.getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(weightlessTraitDTO.getTraitTypeId()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID);
    assertThat(weightlessTraitDTO.getValue()).isEqualTo(VALUE);
    assertThat(weightlessTraitDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE);
  }

  @Test
  void testGetterSetters() {
    // Create with value set 1 initially.
    WeightlessTraitDTO weightlessTraitDTO =
        new WeightlessTraitDTO(
            ID, WEIGHTLESS_TRAIT_ID, TOKEN_ID, WEIGHTLESS_TRAIT_TYPE_ID, VALUE, DISPLAY_TYPE_VALUE);
    // Set value set 2.
    weightlessTraitDTO.setId(ID_2);
    weightlessTraitDTO.setTraitId(WEIGHTLESS_TRAIT_ID_2);
    weightlessTraitDTO.setTokenId(TOKEN_ID_2);
    weightlessTraitDTO.setTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2);
    weightlessTraitDTO.setValue(VALUE_2);
    weightlessTraitDTO.setDisplayTypeValue(DISPLAY_TYPE_VALUE_2);
    // Assert that getters return value set 2.
    assertThat(weightlessTraitDTO.getId()).isEqualTo(ID_2);
    assertThat(weightlessTraitDTO.getTraitId()).isEqualTo(WEIGHTLESS_TRAIT_ID_2);
    assertThat(weightlessTraitDTO.getTokenId()).isEqualTo(TOKEN_ID_2);
    assertThat(weightlessTraitDTO.getTraitTypeId()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID_2);
    assertThat(weightlessTraitDTO.getValue()).isEqualTo(VALUE_2);
    assertThat(weightlessTraitDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE_2);
  }

  @Test
  void testBuilder() {
    WeightlessTraitDTO.WeightlessTraitDTOBuilder builder =
        WeightlessTraitDTO.builder()
            .id(ID)
            .traitId(WEIGHTLESS_TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .value(VALUE)
            .displayTypeValue(DISPLAY_TYPE_VALUE);
    assertThat(builder).isInstanceOf(WeightlessTraitDTO.WeightlessTraitDTOBuilder.class);
    WeightlessTraitDTO weightlessTraitDTO = builder.build();
    assertThat(weightlessTraitDTO).isInstanceOf(WeightlessTraitDTO.class);
    // Assert that getters return value set 1.
    assertThat(weightlessTraitDTO.getId()).isEqualTo(ID);
    assertThat(weightlessTraitDTO.getTraitId()).isEqualTo(WEIGHTLESS_TRAIT_ID);
    assertThat(weightlessTraitDTO.getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(weightlessTraitDTO.getTraitTypeId()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID);
    assertThat(weightlessTraitDTO.getValue()).isEqualTo(VALUE);
    assertThat(weightlessTraitDTO.getDisplayTypeValue()).isEqualTo(DISPLAY_TYPE_VALUE);
  }
}
