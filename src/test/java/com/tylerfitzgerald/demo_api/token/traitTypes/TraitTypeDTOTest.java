package com.tylerfitzgerald.demo_api.token.traitTypes;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class TraitTypeDTOTest {

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
    TraitTypeDTO traitTypeDTO = new TraitTypeDTO(ID, TRAIT_TYPE_ID, TRAIT_TYPE_NAME, DESCRIPTION);
    // Assert that getters return value set 1.
    assertThat(traitTypeDTO.getId()).isEqualTo(ID);
    assertThat(traitTypeDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(traitTypeDTO.getTraitTypeName()).isEqualTo(TRAIT_TYPE_NAME);
    assertThat(traitTypeDTO.getDescription()).isEqualTo(DESCRIPTION);
  }

  @Test
  void testGetterSetters() {
    // Create with value set 1 initially.
    TraitTypeDTO traitTypeDTO = new TraitTypeDTO(ID, TRAIT_TYPE_ID, TRAIT_TYPE_NAME, DESCRIPTION);
    // Set value set 2.
    traitTypeDTO.setId(ID_2);
    traitTypeDTO.setTraitTypeId(TRAIT_TYPE_ID_2);
    traitTypeDTO.setTraitTypeName(TRAIT_TYPE_NAME_2);
    traitTypeDTO.setDescription(DESCRIPTION_2);
    // Assert that getters return value set 2.
    assertThat(traitTypeDTO.getId()).isEqualTo(ID_2);
    assertThat(traitTypeDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID_2);
    assertThat(traitTypeDTO.getTraitTypeName()).isEqualTo(TRAIT_TYPE_NAME_2);
    assertThat(traitTypeDTO.getDescription()).isEqualTo(DESCRIPTION_2);
  }

  @Test
  void testBuilder() {
    TraitTypeDTO.TraitTypeDTOBuilder builder =
        TraitTypeDTO.builder()
            .id(ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeName(TRAIT_TYPE_NAME)
            .description(DESCRIPTION);
    assertThat(builder).isInstanceOf(TraitTypeDTO.TraitTypeDTOBuilder.class);
    TraitTypeDTO traitTypeDTO = builder.build();
    assertThat(traitTypeDTO).isInstanceOf(TraitTypeDTO.class);
    // Assert that getters return value set 1.
    assertThat(traitTypeDTO.getId()).isEqualTo(ID);
    assertThat(traitTypeDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(traitTypeDTO.getTraitTypeName()).isEqualTo(TRAIT_TYPE_NAME);
    assertThat(traitTypeDTO.getDescription()).isEqualTo(DESCRIPTION);
  }
}
