package com.tylerfitzgerald.demo_api.etc.listFinders;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeightedTraitTypesListFinderTest {

  private List<WeightedTraitTypeDTO> weightedTraitTypesList;
  // Value set 1
  private static final Long ID_1 = 1L;
  private static final Long TRAIT_TYPE_ID_1 = 2L;
  private static final String TRAIT_TYPE_NAME_1 = "STRING_A";
  private static final String DESCRIPTION_1 = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 3L;
  private static final Long TRAIT_TYPE_ID_2 = 4L;
  private static final String TRAIT_TYPE_NAME_2 = "STRING_C";
  private static final String DESCRIPTION_2 = "STRING_D";
  // Value set 3
  private static final Long ID_3 = 5L;
  private static final Long TRAIT_TYPE_ID_3 = 6L;
  private static final String TRAIT_TYPE_NAME_3 = "STRING_E";
  private static final String DESCRIPTION_3 = "STRING_F";
  // Not in list values
  private static final Long TRAIT_TYPE_ID_NOT_IN_LIST = 7L;

  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    Math.toIntExact(TRAIT_TYPE_ID_1), Math.toIntExact(TRAIT_TYPE_ID_3)
  };

  @BeforeEach
  public void setup() {
    setupList();
  }

  private void setupList() {
    weightedTraitTypesList = new ArrayList<>();
    weightedTraitTypesList.add(
        WeightedTraitTypeDTO.builder()
            .id(ID_1)
            .traitTypeId(TRAIT_TYPE_ID_1)
            .traitTypeName(TRAIT_TYPE_NAME_1)
            .description(DESCRIPTION_1)
            .build());
    weightedTraitTypesList.add(
        WeightedTraitTypeDTO.builder()
            .id(ID_2)
            .traitTypeId(TRAIT_TYPE_ID_2)
            .traitTypeName(TRAIT_TYPE_NAME_2)
            .description(DESCRIPTION_2)
            .build());
    weightedTraitTypesList.add(
        WeightedTraitTypeDTO.builder()
            .id(ID_3)
            .traitTypeId(TRAIT_TYPE_ID_3)
            .traitTypeName(TRAIT_TYPE_NAME_3)
            .description(DESCRIPTION_3)
            .build());
  }

  @Test
  void testFindFirstByTraitTypeId() {
    WeightedTraitTypesListFinder weightedTraitTypesListFinder = new WeightedTraitTypesListFinder();
    assertThat(
            weightedTraitTypesListFinder.findFirstByTraitTypeId(
                weightedTraitTypesList, TRAIT_TYPE_ID_1))
        .isNotNull();
    assertThat(
            weightedTraitTypesListFinder.findFirstByTraitTypeId(
                weightedTraitTypesList, TRAIT_TYPE_ID_2))
        .isNotNull();
    assertThat(
            weightedTraitTypesListFinder.findFirstByTraitTypeId(
                weightedTraitTypesList, TRAIT_TYPE_ID_3))
        .isNotNull();
    assertThat(
            weightedTraitTypesListFinder.findFirstByTraitTypeId(
                weightedTraitTypesList, TRAIT_TYPE_ID_NOT_IN_LIST))
        .isNull();
  }

  @Test
  void testFindByIgnoringTraitTypeIdList() {
    WeightedTraitTypesListFinder weightedTraitTypesListFinder = new WeightedTraitTypesListFinder();
    List<WeightedTraitTypeDTO> weightedTraitsList =
        weightedTraitTypesListFinder.findByIgnoringTraitTypeIdList(
            weightedTraitTypesList, WEIGHTED_TRAIT_TYPES_TO_IGNORE);
    assertThat(weightedTraitsList.size()).isEqualTo(1);
    assertThat(weightedTraitsList.get(0).getTraitTypeId()).isEqualTo(TRAIT_TYPE_ID_2);
  }
}
