package com.tylerfitzgerald.demo_api.sql.tblTraits;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TraitDTOTest {

  // Value set 1
  private static final Long ID = 1L;
  private static final Long TRAIT_ID = 2L;
  private static final Long TOKEN_ID = 3L;
  private static final Long TRAIT_TYPE_ID = 4L;
  private static final Long TRAIT_TYPE_WEIGHT_ID = 5L;
  // Value set 2
  private static final Long ID_2 = 6L;
  private static final Long TRAIT_ID_2 = 7L;
  private static final Long TOKEN_ID_2 = 8L;
  private static final Long TRAIT_TYPE_ID_2 = 9L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_2 = 10L;

  @Test
  void testConstructor() {
    // Create with value set 1.
    TraitDTO traitDTO = new TraitDTO(ID, TRAIT_ID, TOKEN_ID, TRAIT_TYPE_ID, TRAIT_TYPE_WEIGHT_ID);
    // Assert that getters return value set 1.
    assertThat(traitDTO.getId()).isEqualTo(ID);
    assertThat(traitDTO.getTraitId()).isEqualTo(TRAIT_ID);
    assertThat(traitDTO.getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(traitDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(traitDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
  }

  @Test
  void testGetterSetters() {
    // Create with value set 1 initially.
    TraitDTO traitDTO = new TraitDTO(ID, TRAIT_ID, TOKEN_ID, TRAIT_TYPE_ID, TRAIT_TYPE_WEIGHT_ID);
    // Set value set 2.
    traitDTO.setId(ID_2);
    traitDTO.setTraitId(TRAIT_ID_2);
    traitDTO.setTokenId(TOKEN_ID_2);
    traitDTO.setTraitTypeId(TRAIT_TYPE_ID_2);
    traitDTO.setTraitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2);
    // Assert that getters return value set 2.
    assertThat(traitDTO.getId()).isEqualTo(ID_2);
    assertThat(traitDTO.getTraitId()).isEqualTo(TRAIT_ID_2);
    assertThat(traitDTO.getTokenId()).isEqualTo(TOKEN_ID_2);
    assertThat(traitDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID_2);
    assertThat(traitDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID_2);
  }

  @Test
  void testBuilder() {
    TraitDTO.TraitDTOBuilder builder =
        TraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID);
    assertThat(builder).isInstanceOf(TraitDTO.TraitDTOBuilder.class);
    TraitDTO traitDTO = builder.build();
    assertThat(traitDTO).isInstanceOf(TraitDTO.class);
    // Assert that getters return value set 1.
    assertThat(traitDTO.getId()).isEqualTo(ID);
    assertThat(traitDTO.getTraitId()).isEqualTo(TRAIT_ID);
    assertThat(traitDTO.getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(traitDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(traitDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
  }
}
