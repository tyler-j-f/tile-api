package com.tylerfitzgerald.demo_api.etc.listFinders;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.sql.tblWeightlessTraitTypes.WeightlessTraitTypeDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeightlessTraitTypesFinderTest {

  private List<WeightlessTraitTypeDTO> weightlessTraitTypesList;
  // Value set 1
  private static final Long ID_1 = 1L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_1 = 2L;
  private static final String NAME_1 = "STRING_A";
  private static final String DESCRIPTION_1 = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 3L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_2 = 4L;
  private static final String NAME_2 = "STRING_C";
  private static final String DESCRIPTION_2 = "STRING_D";
  // Value set 3
  // Value set 2
  private static final Long ID_3 = 5L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_3 = 6L;
  private static final String NAME_3 = "STRING_E";
  private static final String DESCRIPTION_3 = "STRING_F";
  // Not in list values
  private static final Long TRAIT_TYPE_ID_NOT_IN_LIST = 5L;

  private static final int[] WEIGHTLESS_TRAIT_TYPES_TO_IGNORE = {
    Math.toIntExact(WEIGHTLESS_TRAIT_TYPE_ID_2), Math.toIntExact(WEIGHTLESS_TRAIT_TYPE_ID_3)
  };

  @BeforeEach
  public void setup() {
    setupList();
  }

  private void setupList() {
    weightlessTraitTypesList = new ArrayList<>();
    weightlessTraitTypesList.add(
        WeightlessTraitTypeDTO.builder()
            .id(ID_1)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .weightlessTraitTypeName(NAME_1)
            .description(DESCRIPTION_1)
            .build());
    weightlessTraitTypesList.add(
        WeightlessTraitTypeDTO.builder()
            .id(ID_2)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .weightlessTraitTypeName(NAME_2)
            .description(DESCRIPTION_2)
            .build());
    weightlessTraitTypesList.add(
        WeightlessTraitTypeDTO.builder()
            .id(ID_3)
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_3)
            .weightlessTraitTypeName(NAME_3)
            .description(DESCRIPTION_3)
            .build());
  }

  @Test
  void testConstructor() {
    assertThat(new WeightlessTraitTypesFinder()).isInstanceOf(WeightlessTraitTypesFinder.class);
  }

  @Test
  void testFindFirstByTraitTypeId() {
    WeightlessTraitTypesFinder weightlessTraitTypesFinder = new WeightlessTraitTypesFinder();
    assertThat(
            weightlessTraitTypesFinder.findFirstByWeightlessTraitTypeId(
                weightlessTraitTypesList, WEIGHTLESS_TRAIT_TYPE_ID_1))
        .isNotNull();
    assertThat(
            weightlessTraitTypesFinder.findFirstByWeightlessTraitTypeId(
                weightlessTraitTypesList, WEIGHTLESS_TRAIT_TYPE_ID_2))
        .isNotNull();
    assertThat(
            weightlessTraitTypesFinder.findFirstByWeightlessTraitTypeId(
                weightlessTraitTypesList, WEIGHTLESS_TRAIT_TYPE_ID_3))
        .isNotNull();
    assertThat(
            weightlessTraitTypesFinder.findFirstByWeightlessTraitTypeId(
                weightlessTraitTypesList, TRAIT_TYPE_ID_NOT_IN_LIST))
        .isNull();
  }

  @Test
  void testFindByIgnoringTraitTypeIdList() {
    WeightlessTraitTypesFinder weightlessTraitTypesFinder = new WeightlessTraitTypesFinder();
    List<WeightlessTraitTypeDTO> weightlessTraitsList =
        weightlessTraitTypesFinder.findByIgnoringTraitTypeIdList(
            weightlessTraitTypesList, WEIGHTLESS_TRAIT_TYPES_TO_IGNORE);
    assertThat(weightlessTraitsList.size()).isEqualTo(1);
    assertThat(weightlessTraitsList.get(0).getWeightlessTraitTypeId())
        .isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID_1);
  }
}
