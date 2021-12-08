package com.tylerfitzgerald.demo_api.sql.tbls.tblTraitTypes;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class WeightedTraitTypeDTOTest {

  // Value set 1
  private static final Long ID = 1L;
  private static final Long TRAIT_TYPE_ID = 2L;
  private static final String TRAIT_TYPE_NAME = "STRING_A";
  private static final String DESCRIPTION = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 3L;
  private static final Long TRAIT_TYPE_ID_2 = 4L;
  private static final String TRAIT_TYPE_NAME_2 = "STRING_C";
  private static final String DESCRIPTION_2 = "STRING_D";

  @Test
  void testConstructor() {
    // Create with value set 1.
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        new WeightedTraitTypeDTO(ID, TRAIT_TYPE_ID, TRAIT_TYPE_NAME, DESCRIPTION);
    // Assert that getters return value set 1.
    assertThat(weightedTraitTypeDTO.getId()).isEqualTo(ID);
    assertThat(weightedTraitTypeDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(weightedTraitTypeDTO.getTraitTypeName()).isEqualTo(TRAIT_TYPE_NAME);
    assertThat(weightedTraitTypeDTO.getDescription()).isEqualTo(DESCRIPTION);
  }

  @Test
  void testGetterSetters() {
    // Create with value set 1 initially.
    WeightedTraitTypeDTO weightedTraitTypeDTO =
        new WeightedTraitTypeDTO(ID, TRAIT_TYPE_ID, TRAIT_TYPE_NAME, DESCRIPTION);
    // Set value set 2.
    weightedTraitTypeDTO.setId(ID_2);
    weightedTraitTypeDTO.setTraitTypeId(TRAIT_TYPE_ID_2);
    weightedTraitTypeDTO.setTraitTypeName(TRAIT_TYPE_NAME_2);
    weightedTraitTypeDTO.setDescription(DESCRIPTION_2);
    // Assert that getters return value set 2.
    assertThat(weightedTraitTypeDTO.getId()).isEqualTo(ID_2);
    assertThat(weightedTraitTypeDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID_2);
    assertThat(weightedTraitTypeDTO.getTraitTypeName()).isEqualTo(TRAIT_TYPE_NAME_2);
    assertThat(weightedTraitTypeDTO.getDescription()).isEqualTo(DESCRIPTION_2);
  }

  @Test
  void testBuilder() {
    WeightedTraitTypeDTO.WeightedTraitTypeDTOBuilder builder =
        WeightedTraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION);
    assertThat(builder).isInstanceOf(WeightedTraitTypeDTO.WeightedTraitTypeDTOBuilder.class);
    WeightedTraitTypeDTO weightedTraitTypeDTO = builder.build();
    assertThat(weightedTraitTypeDTO).isInstanceOf(WeightedTraitTypeDTO.class);
    // Assert that getters return value set 1.
    assertThat(weightedTraitTypeDTO.getId()).isEqualTo(ID);
    assertThat(weightedTraitTypeDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(weightedTraitTypeDTO.getTraitTypeName()).isEqualTo(TRAIT_TYPE_NAME);
    assertThat(weightedTraitTypeDTO.getDescription()).isEqualTo(DESCRIPTION);
  }
}
