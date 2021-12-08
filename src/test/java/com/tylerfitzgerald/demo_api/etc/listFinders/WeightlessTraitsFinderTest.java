package com.tylerfitzgerald.demo_api.etc.listFinders;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class WeightlessTraitsFinderTest {

  private List<WeightlessTraitDTO> weightlessTraitsList;
  // Value set 1
  private static final Long ID_1 = 1L;
  private static final Long WEIGHTLESS_TRAIT_ID_1 = 2L;
  private static final Long TOKEN_ID_1 = 3L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_1 = 4L;
  private static final String VALUE_1 = "STRING_A";
  private static final String DISPLAY_TYPE_VALUE_1 = "STRING_B";
  // Value set 2
  private static final Long ID_2 = 5L;
  private static final Long WEIGHTLESS_TRAIT_ID_2 = 6L;
  private static final Long TOKEN_ID_2 = 7L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_2 = 8L;
  private static final String VALUE_2 = "STRING_C";
  private static final String DISPLAY_TYPE_VALUE_2 = "STRING_D";
  // Value set 3
  private static final Long ID_3 = 9L;
  private static final Long WEIGHTLESS_TRAIT_ID_3 = 10L;
  private static final Long TOKEN_ID_3 = 11L;
  private static final Long WEIGHTLESS_TRAIT_TYPE_ID_3 = 12L;
  private static final String VALUE_3 = "STRING_E";
  private static final String DISPLAY_TYPE_VALUE_3 = "STRING_F";
  // Not in list values
  private static final Long TRAIT_TYPE_ID_NOT_IN_LIST = 13L;

  @BeforeEach
  public void setup() {
    setupList();
  }

  private void setupList() {
    weightlessTraitsList = new ArrayList<>();
    weightlessTraitsList.add(
        WeightlessTraitDTO.builder()
            .id(ID_1)
            .traitId(WEIGHTLESS_TRAIT_ID_1)
            .tokenId(TOKEN_ID_1)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .value(VALUE_1)
            .displayTypeValue(DISPLAY_TYPE_VALUE_1)
            .build());
    weightlessTraitsList.add(
        WeightlessTraitDTO.builder()
            .id(ID_2)
            .traitId(WEIGHTLESS_TRAIT_ID_2)
            .tokenId(TOKEN_ID_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .value(VALUE_2)
            .displayTypeValue(DISPLAY_TYPE_VALUE_2)
            .build());
    weightlessTraitsList.add(
        WeightlessTraitDTO.builder()
            .id(ID_3)
            .traitId(WEIGHTLESS_TRAIT_ID_3)
            .tokenId(TOKEN_ID_3)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_3)
            .value(VALUE_3)
            .displayTypeValue(DISPLAY_TYPE_VALUE_3)
            .build());
  }

  @Test
  void testConstructor() {
    assertThat(new WeightlessTraitsFinder()).isInstanceOf(WeightlessTraitsFinder.class);
  }

  @Test
  void testFindFirstByTraitTypeId() {
    WeightlessTraitsFinder weightlessTraitsFinder = new WeightlessTraitsFinder();
    assertThat(
            weightlessTraitsFinder.findFirstByTraitTypeId(
                weightlessTraitsList, WEIGHTLESS_TRAIT_TYPE_ID_1))
        .isNotNull();
    assertThat(
            weightlessTraitsFinder.findFirstByTraitTypeId(
                weightlessTraitsList, WEIGHTLESS_TRAIT_TYPE_ID_2))
        .isNotNull();
    assertThat(
            weightlessTraitsFinder.findFirstByTraitTypeId(
                weightlessTraitsList, WEIGHTLESS_TRAIT_TYPE_ID_3))
        .isNotNull();
    assertThat(
            weightlessTraitsFinder.findFirstByTraitTypeId(
                weightlessTraitsList, TRAIT_TYPE_ID_NOT_IN_LIST))
        .isNull();
  }
}
