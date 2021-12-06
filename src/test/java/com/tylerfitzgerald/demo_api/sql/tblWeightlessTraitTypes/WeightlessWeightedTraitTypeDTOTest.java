package com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeightlessWeightedTraitTypeDTOTest {

  // Value set 1
  private static final Long ID = 1L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID = 2L;
  private static final String NAME = "STRING_A";
  private static final String DESCRIPTION = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 3L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_2 = 4L;
  private static final String NAME_2 = "STRING_C";
  private static final String DESCRIPTION_2 = "STRING_D";

  @Test
  void testConstructor() {
    // Create with value set 1.
    WeightlessTraitTypeDTO weightlessTraitTypeDTO =
        new WeightlessTraitTypeDTO(ID, WEIGHTLESS_TRAIT_TYPE_ID, NAME, DESCRIPTION);
    // Assert that getters return value set 1.
    assertThat(weightlessTraitTypeDTO.getId()).isEqualTo(ID);
    assertThat(weightlessTraitTypeDTO.getWeightlessTraitTypeId())
        .isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID);
    assertThat(weightlessTraitTypeDTO.getWeightlessTraitTypeName()).isEqualTo(NAME);
    assertThat(weightlessTraitTypeDTO.getDescription()).isEqualTo(DESCRIPTION);
  }

  @Test
  void testGetterSetters() {
    // Create with value set 1 initially.
    WeightlessTraitTypeDTO weightlessTraitTypeDTO =
        new WeightlessTraitTypeDTO(ID, WEIGHTLESS_TRAIT_TYPE_ID, NAME, DESCRIPTION);
    // Set value set 2.
    weightlessTraitTypeDTO.setId(ID_2);
    weightlessTraitTypeDTO.setWeightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2);
    weightlessTraitTypeDTO.setWeightlessTraitTypeName(NAME_2);
    weightlessTraitTypeDTO.setDescription(DESCRIPTION_2);
    // Assert that getters return value set 2.
    assertThat(weightlessTraitTypeDTO.getId()).isEqualTo(ID_2);
    assertThat(weightlessTraitTypeDTO.getWeightlessTraitTypeId())
        .isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID_2);
    assertThat(weightlessTraitTypeDTO.getWeightlessTraitTypeName()).isEqualTo(NAME_2);
    assertThat(weightlessTraitTypeDTO.getDescription()).isEqualTo(DESCRIPTION_2);
  }

  @Test
  void testBuilder() {
    WeightlessTraitTypeDTO.WeightlessTraitTypeDTOBuilder builder =
        WeightlessTraitTypeDTO.builder()
            .id(ID)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID)
            .weightlessTraitTypeName(NAME)
            .description(DESCRIPTION);
    assertThat(builder).isInstanceOf(WeightlessTraitTypeDTO.WeightlessTraitTypeDTOBuilder.class);
    WeightlessTraitTypeDTO weightlessTraitDTO = builder.build();
    assertThat(weightlessTraitDTO).isInstanceOf(WeightlessTraitTypeDTO.class);
    // Assert that getters return value set 1.
    assertThat(weightlessTraitDTO.getId()).isEqualTo(ID);
    assertThat(weightlessTraitDTO.getWeightlessTraitTypeId()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID);
    assertThat(weightlessTraitDTO.getWeightlessTraitTypeName()).isEqualTo(NAME);
    assertThat(weightlessTraitDTO.getDescription()).isEqualTo(DESCRIPTION);
  }
}
