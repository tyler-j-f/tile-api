package com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.erc721.token.initializers.TokenInitializeException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.WeightlessTraitException;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.ColorTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.EmojiTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.MergeRarityTraitPicker;
import com.tylerfitzgerald.demo_api.erc721.token.traits.weightlessTraits.traitPickers.OverallRarityTraitPicker;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightlessTraitsListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class InitializeTokenWeightlessTraitsCreatorTest {
  private final Long WEIGHTED_TRAIT_TYPE_ID_1 = 1L;
  private final Long WEIGHTED_TRAIT_TYPE_ID_2 = 2L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1 = 3L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2 = 4L;
  private final Long WEIGHTLESS_TRAIT_TYPE_ID_1 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_2_EMOJI);
  private final Long WEIGHTLESS_TRAIT_TYPE_ID_2 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_3_COLOR);
  private final Long WEIGHTLESS_TRAIT_TYPE_ID_3 =
      Long.valueOf(WeightlessTraitTypeConstants.OVERALL_RARITY);
  private final Long WEIGHTLESS_TRAIT_TYPE_ID_4 = 100L;
  private final String WEIGHTLESS_TRAIT_TYPE_NAME_1 = "A";
  private final String WEIGHTLESS_TRAIT_TYPE_NAME_2 = "B";
  private final String WEIGHTLESS_TRAIT_TYPE_NAME_3 = "C";
  private final String WEIGHTLESS_TRAIT_TYPE_NAME_4 = "D";
  private final String WEIGHTLESS_TRAIT_TYPE_DESCRIPTION_1 = "E";
  private final String WEIGHTLESS_TRAIT_TYPE_DESCRIPTION_2 = "F";
  private final String WEIGHTLESS_TRAIT_TYPE_DESCRIPTION_3 = "G";
  private final String WEIGHTLESS_TRAIT_TYPE_DESCRIPTION_4 = "H";
  private final String WEIGHTLESS_TRAIT_TYPE_VALUE_1 = "I";
  private final String WEIGHTLESS_TRAIT_TYPE_VALUE_2 = "J";
  private final String WEIGHTLESS_TRAIT_TYPE_VALUE_3 = "K";
  private final String WEIGHTLESS_TRAIT_TYPE_VALUE_4 = "invalid weightlessTraitValue";
  private final Long TOKEN_ID = 9L;
  private final Long WEIGHTLESS_TRAITS_IN_REPO_SIZE = 10L;
  private final Long NEW_TRAIT_ID_1 = 41L;
  private final Long NEW_TRAIT_ID_2 = 42L;
  private final Long SEED_FOR_TRAITS = 123123321L;
  private WeightlessTraitDTO newTrait1;
  private WeightlessTraitDTO newTrait2;
  private WeightlessTraitDTO newTrait3;
  private WeightlessTraitDTO newTrait4;
  private List<WeightlessTraitDTO> mockNewTraits = new ArrayList<>();
  private List<WeightlessTraitTypeDTO> mockWeightlessTraitTypes = new ArrayList<>();
  private List<WeightedTraitTypeWeightDTO> mockWeightedTraitTypeWeights = new ArrayList<>();
  private List<WeightedTraitTypeDTO> mockWeightedTraitTypes = new ArrayList<>();
  @Mock private WeightlessTraitRepository weightlessTraitRepository;
  @Mock protected WeightlessTraitsListFinder weightlessTraitInListFinder;
  @Mock protected MergeRarityTraitPicker mergeRarityTraitPicker;
  @Mock protected EmojiTraitPicker emojiTraitPicker;
  @Mock protected ColorTraitPicker colorTraitPicker;
  @Mock protected OverallRarityTraitPicker overallRarityTraitPicker;

  @InjectMocks
  private InitializeTokenWeightlessTraitsCreator weightlessTraitsCreator =
      new InitializeTokenWeightlessTraitsCreator();

  @Captor ArgumentCaptor<WeightlessTraitDTO> weightlessTraitDTOCaptor;

  @Test
  void testCreateTraits() throws TokenInitializeException, WeightlessTraitException {
    setUpMocks();
    weightlessTraitsCreator.createTraits(buildContext());
    assertions();
  }

  private void mockNewTraits() {
    newTrait1 =
        WeightlessTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(NEW_TRAIT_ID_1)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .build();
    newTrait2 =
        WeightlessTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(NEW_TRAIT_ID_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .build();
    newTrait3 =
        WeightlessTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(NEW_TRAIT_ID_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_3)
            .build();
    newTrait4 =
        WeightlessTraitDTO.builder()
            .tokenId(TOKEN_ID)
            .traitId(NEW_TRAIT_ID_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_4)
            .build();
    mockNewTraits.add(newTrait1);
    mockNewTraits.add(newTrait2);
    mockNewTraits.add(newTrait3);
    mockNewTraits.add(newTrait4);
  }

  private void setUpMocks() throws WeightlessTraitException {
    mockNewTraits();
    mockWeightlessTraitTypes();
    mockWeightedTraitTypes();
    mockWeightedTraitTypeWeights();
    mockGetWeightlessTraitValues();
    Mockito.when(weightlessTraitRepository.create(weightlessTraitDTOCaptor.capture()))
        .thenReturn(newTrait1, newTrait2, newTrait3, newTrait4);
    Mockito.when(weightlessTraitRepository.getCount())
        .thenReturn(
            WEIGHTLESS_TRAITS_IN_REPO_SIZE,
            WEIGHTLESS_TRAITS_IN_REPO_SIZE + 1,
            WEIGHTLESS_TRAITS_IN_REPO_SIZE + 2,
            WEIGHTLESS_TRAITS_IN_REPO_SIZE + 3);
  }

  private void mockWeightlessTraitTypes() {
    mockWeightlessTraitTypes.add(
        WeightlessTraitTypeDTO.builder()
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .weightlessTraitTypeName(WEIGHTLESS_TRAIT_TYPE_NAME_1)
            .description(WEIGHTLESS_TRAIT_TYPE_DESCRIPTION_1)
            .build());
    mockWeightlessTraitTypes.add(
        WeightlessTraitTypeDTO.builder()
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .weightlessTraitTypeName(WEIGHTLESS_TRAIT_TYPE_NAME_2)
            .description(WEIGHTLESS_TRAIT_TYPE_DESCRIPTION_2)
            .build());
    mockWeightlessTraitTypes.add(
        WeightlessTraitTypeDTO.builder()
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_3)
            .weightlessTraitTypeName(WEIGHTLESS_TRAIT_TYPE_NAME_3)
            .description(WEIGHTLESS_TRAIT_TYPE_DESCRIPTION_3)
            .build());
    mockWeightlessTraitTypes.add(
        WeightlessTraitTypeDTO.builder()
            .weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_4)
            .weightlessTraitTypeName(WEIGHTLESS_TRAIT_TYPE_NAME_4)
            .description(WEIGHTLESS_TRAIT_TYPE_DESCRIPTION_4)
            .build());
    return;
  }

  private void mockWeightedTraitTypes() {
    mockWeightedTraitTypes.add(
        WeightedTraitTypeDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1).build());
    mockWeightedTraitTypes.add(
        WeightedTraitTypeDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2).build());
    return;
  }

  private void mockWeightedTraitTypeWeights() {
    mockWeightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1).build());
    mockWeightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2).build());
    return;
  }

  private void assertions() {
    Mockito.verify(weightlessTraitRepository, Mockito.times(mockNewTraits.size())).getCount();
    Mockito.verify(weightlessTraitRepository, Mockito.times(mockNewTraits.size()))
        .create(Mockito.any());
    assertForTraitRepositoryCreateCalls();
  }

  private void mockGetWeightlessTraitValues() throws WeightlessTraitException {
    Mockito.when(emojiTraitPicker.getValue(Mockito.any()))
        .thenReturn(WEIGHTLESS_TRAIT_TYPE_VALUE_1);
    Mockito.when(colorTraitPicker.getValue(Mockito.any()))
        .thenReturn(WEIGHTLESS_TRAIT_TYPE_VALUE_2);
    Mockito.when(overallRarityTraitPicker.getValue(Mockito.any()))
        .thenReturn(WEIGHTLESS_TRAIT_TYPE_VALUE_3);
  }

  private TraitsCreatorContext buildContext() {
    return TraitsCreatorContext.builder()
        .tokenId(TOKEN_ID)
        .seedForTraits(SEED_FOR_TRAITS)
        .weightedTraitTypes(mockWeightedTraitTypes)
        .weightlessTraitTypes(mockWeightlessTraitTypes)
        .build();
  }

  private void assertForTraitRepositoryCreateCalls() {
    List<WeightlessTraitDTO> values = weightlessTraitDTOCaptor.getAllValues();
    assertThat(values.get(0).getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(values.get(1).getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(values.get(2).getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(values.get(3).getTokenId()).isEqualTo(TOKEN_ID);
    assertThat(values.get(0).getValue()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_VALUE_1);
    assertThat(values.get(1).getValue()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_VALUE_2);
    assertThat(values.get(2).getValue()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_VALUE_3);
    assertThat(values.get(3).getValue()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_VALUE_4);
    assertThat(values.get(0).getTraitTypeId()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID_1);
    assertThat(values.get(1).getTraitTypeId()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID_2);
    assertThat(values.get(2).getTraitTypeId()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID_3);
    assertThat(values.get(3).getTraitTypeId()).isEqualTo(WEIGHTLESS_TRAIT_TYPE_ID_4);
    // For each trait, Add 1 to WEIGHTLESS_TRAITS_IN_REPO_SIZE, since we will have mocked creating 1
    // more trait
    assertThat(values.get(0).getTraitId()).isEqualTo(WEIGHTLESS_TRAITS_IN_REPO_SIZE + 1);
    assertThat(values.get(1).getTraitId()).isEqualTo(WEIGHTLESS_TRAITS_IN_REPO_SIZE + 2);
    assertThat(values.get(2).getTraitId()).isEqualTo(WEIGHTLESS_TRAITS_IN_REPO_SIZE + 3);
    assertThat(values.get(3).getTraitId()).isEqualTo(WEIGHTLESS_TRAITS_IN_REPO_SIZE + 4);
  }
}
