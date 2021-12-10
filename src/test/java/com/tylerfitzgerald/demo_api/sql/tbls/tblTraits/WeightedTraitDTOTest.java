package com.tylerfitzgerald.demo_api.sql.tbls.tblTraits;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import org.junit.jupiter.api.Test;

public class WeightedTraitDTOTest {

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
    WeightedTraitDTO weightedTraitDTO =
        new WeightedTraitDTO(ID, TRAIT_ID, TOKEN_ID, TRAIT_TYPE_ID, TRAIT_TYPE_WEIGHT_ID);
    // Assert that getters return value set 1.
    assertThat(weightedTraitDTO.getId()).isEqualTo(ID);
    assertThat(weightedTraitDTO.getTraitId()).isEqualTo(TRAIT_ID);
    assertThat(weightedTraitDTO.getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(weightedTraitDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(weightedTraitDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
  }

  @Test
  void testGetterSetters() {
    // Create with value set 1 initially.
    WeightedTraitDTO weightedTraitDTO =
        new WeightedTraitDTO(ID, TRAIT_ID, TOKEN_ID, TRAIT_TYPE_ID, TRAIT_TYPE_WEIGHT_ID);
    // Set value set 2.
    weightedTraitDTO.setId(ID_2);
    weightedTraitDTO.setTraitId(TRAIT_ID_2);
    weightedTraitDTO.setTokenId(TOKEN_ID_2);
    weightedTraitDTO.setTraitTypeId(TRAIT_TYPE_ID_2);
    weightedTraitDTO.setTraitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2);
    // Assert that getters return value set 2.
    assertThat(weightedTraitDTO.getId()).isEqualTo(ID_2);
    assertThat(weightedTraitDTO.getTraitId()).isEqualTo(TRAIT_ID_2);
    assertThat(weightedTraitDTO.getTokenId()).isEqualTo(TOKEN_ID_2);
    assertThat(weightedTraitDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID_2);
    assertThat(weightedTraitDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID_2);
  }

  @Test
  void testBuilder() {
    WeightedTraitDTO.WeightedTraitDTOBuilder builder =
        WeightedTraitDTO.builder()
            .id(ID)
            .traitId(TRAIT_ID)
            .tokenId(TOKEN_ID)
            .traitTypeId(TRAIT_TYPE_ID)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID);
    assertThat(builder).isInstanceOf(WeightedTraitDTO.WeightedTraitDTOBuilder.class);
    WeightedTraitDTO weightedTraitDTO = builder.build();
    assertThat(weightedTraitDTO).isInstanceOf(WeightedTraitDTO.class);
    // Assert that getters return value set 1.
    assertThat(weightedTraitDTO.getId()).isEqualTo(ID);
    assertThat(weightedTraitDTO.getTraitId()).isEqualTo(TRAIT_ID);
    assertThat(weightedTraitDTO.getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(weightedTraitDTO.getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID);
    assertThat(weightedTraitDTO.getTraitTypeWeightId()).isEqualTo(TRAIT_TYPE_WEIGHT_ID);
  }
}
