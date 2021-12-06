package com.tylerfitzgerald.demo_api.listUtils.finders;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.sql.tblTraits.WeightedTraitDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeightedTraitsListHelperTest {

  private List<WeightedTraitDTO> weightedTraitsList;
  // Value set 1
  private static final Long ID_1 = 1L;
  private static final Long TRAIT_ID_1 = 2L;
  private static final Long TOKEN_ID_1 = 3L;
  private static final Long TRAIT_TYPE_ID_1 = 4L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_1 = 5L;
  // Value set 2
  private static final Long ID_2 = 6L;
  private static final Long TRAIT_ID_2 = 7L;
  private static final Long TOKEN_ID_2 = 8L;
  private static final Long TRAIT_TYPE_ID_2 = 9L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_2 = 10L;
  // Value set 3
  private static final Long ID_3 = 11L;
  private static final Long TRAIT_ID_3 = 12L;
  private static final Long TOKEN_ID_3 = 13L;
  private static final Long TRAIT_TYPE_ID_3 = 14L;
  private static final Long TRAIT_TYPE_WEIGHT_ID_3 = 15L;
  // Not in list values
  private static final Long TRAIT_TYPE_ID_NOT_IN_LIST = 16L;

  @BeforeEach
  public void setup() {
    setupList();
  }

  private void setupList() {
    weightedTraitsList = new ArrayList<>();
    weightedTraitsList.add(
        WeightedTraitDTO.builder()
            .id(ID_1)
            .traitId(TRAIT_ID_1)
            .tokenId(TOKEN_ID_1)
            .traitTypeId(TRAIT_TYPE_ID_1)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_1)
            .build());
    weightedTraitsList.add(
        WeightedTraitDTO.builder()
            .id(ID_2)
            .traitId(TRAIT_ID_2)
            .tokenId(TOKEN_ID_2)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_2)
            .build());
    weightedTraitsList.add(
        WeightedTraitDTO.builder()
            .id(ID_3)
            .traitId(TRAIT_ID_3)
            .tokenId(TOKEN_ID_3)
            .traitTypeId(TRAIT_TYPE_ID_3)
            .traitTypeWeightId(TRAIT_TYPE_WEIGHT_ID_3)
            .build());
  }

  @Test
  void testConstructor() {
    assertThat(new WeightedTraitsListHelper()).isInstanceOf(WeightedTraitsListHelper.class);
  }

  @Test
  void testCanFindElementInList() {
    WeightedTraitsListHelper weightedTraitsListHelper = new WeightedTraitsListHelper();
    assertThat(weightedTraitsListHelper.findByTraitTypeId(weightedTraitsList, TRAIT_TYPE_ID_1))
        .isNotNull();
    assertThat(weightedTraitsListHelper.findByTraitTypeId(weightedTraitsList, TRAIT_TYPE_ID_2))
        .isNotNull();
    assertThat(weightedTraitsListHelper.findByTraitTypeId(weightedTraitsList, TRAIT_TYPE_ID_3))
        .isNotNull();
    assertThat(
            weightedTraitsListHelper.findByTraitTypeId(
                weightedTraitsList, TRAIT_TYPE_ID_NOT_IN_LIST))
        .isNull();
  }
}
