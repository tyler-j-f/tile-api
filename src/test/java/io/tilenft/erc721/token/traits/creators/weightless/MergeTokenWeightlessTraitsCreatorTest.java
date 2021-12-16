package io.tilenft.erc721.token.traits.creators.weightless;

import static org.assertj.core.api.Assertions.assertThat;

import io.tilenft.erc721.token.TokenFacadeDTO;
import io.tilenft.erc721.token.initializers.TokenInitializeException;
import io.tilenft.erc721.token.traits.creators.TraitsCreatorContext;
import io.tilenft.erc721.token.traits.weightless.WeightlessTraitTypeConstants;
import io.tilenft.erc721.token.traits.weightless.traitPickers.MergeRarityTraitPicker;
import io.tilenft.erc721.token.traits.weightless.traitPickers.OverallRarityTraitPicker;
import io.tilenft.erc721.token.traits.weightless.traitPickers.WeightlessTraitPickerException;
import io.tilenft.etc.lists.finders.WeightlessTraitsListFinder;
import io.tilenft.sql.dtos.TokenDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import io.tilenft.sql.repositories.WeightlessTraitRepository;
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
public class MergeTokenWeightlessTraitsCreatorTest {
  private final Long WEIGHTED_TRAIT_TYPE_ID_1 = 1L;
  private final Long WEIGHTED_TRAIT_TYPE_ID_2 = 2L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1 = 3L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2 = 4L;
  private final Long BURNED_TOKEN_ID_1 = 5L;
  private final Long BURNED_TOKEN_ID_2 = 6L;
  private final Long WEIGHTLESS_TRAIT_TYPE_ID_1 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_1_RARITY);
  private final Long WEIGHTLESS_TRAIT_TYPE_ID_2 =
      Long.valueOf(WeightlessTraitTypeConstants.OVERALL_RARITY);
  private final Long WEIGHTLESS_TRAIT_TYPE_ID_3 =
      Long.valueOf(WeightlessTraitTypeConstants.TILE_3_EMOJI);
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
  private final String WEIGHTLESS_TRAIT_TYPE_VALUE_4 = "invalid mergeWeightlessTraitValue";
  private final String BURNED_TOKEN_1_VALUE = "L";
  private final String BURNED_TOKEN_2_VALUE = "M";
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
  private TokenFacadeDTO burnedNft1;
  private TokenFacadeDTO burnedNft2;
  List<WeightlessTraitDTO> burnedNft1WeightlessTraits = new ArrayList<>();
  List<WeightlessTraitDTO> burnedNft2WeightlessTraits = new ArrayList<>();
  @Mock private WeightlessTraitsListFinder weightlessTraitInListFinder;
  @Mock private MergeRarityTraitPicker mergeRarityTraitPicker;
  @Mock private WeightlessTraitRepository weightlessTraitRepository;
  @Mock protected OverallRarityTraitPicker overallRarityTraitPicker;

  @InjectMocks
  private MergeTokenWeightlessTraitsCreator weightlessTraitsCreator =
      new MergeTokenWeightlessTraitsCreator();

  @Captor ArgumentCaptor<WeightlessTraitDTO> weightlessTraitDTOCaptor;

  @Test
  void testCreateTraits() throws TokenInitializeException, WeightlessTraitPickerException {
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

  private void mockBurnedTokens() {
    burnedNft1WeightlessTraits.add(
        WeightlessTraitDTO.builder()
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_3)
            .value(BURNED_TOKEN_1_VALUE)
            .build());
    burnedNft1 =
        TokenFacadeDTO.builder()
            .tokenDTO(TokenDTO.builder().tokenId(BURNED_TOKEN_ID_1).build())
            .weightlessTraits(burnedNft1WeightlessTraits)
            .build();
    burnedNft2WeightlessTraits.add(
        WeightlessTraitDTO.builder()
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_4)
            .value(BURNED_TOKEN_2_VALUE)
            .build());
    burnedNft2 =
        TokenFacadeDTO.builder()
            .tokenDTO(TokenDTO.builder().tokenId(BURNED_TOKEN_ID_2).build())
            .weightlessTraits(burnedNft2WeightlessTraits)
            .build();
  }

  private void setUpMocks() throws WeightlessTraitPickerException {
    mockNewTraits();
    mockWeightlessTraitTypes();
    mockWeightedTraitTypes();
    mockWeightedTraitTypeWeights();
    mockGetWeightlessTraitValues();
    mockBurnedTokens();
    Mockito.when(weightlessTraitRepository.create(weightlessTraitDTOCaptor.capture()))
        .thenReturn(newTrait1, newTrait2, newTrait3, newTrait4);
    Mockito.when(weightlessTraitRepository.getCount())
        .thenReturn(
            WEIGHTLESS_TRAITS_IN_REPO_SIZE,
            WEIGHTLESS_TRAITS_IN_REPO_SIZE + 1,
            WEIGHTLESS_TRAITS_IN_REPO_SIZE + 2,
            WEIGHTLESS_TRAITS_IN_REPO_SIZE + 3);
    Mockito.when(
            weightlessTraitInListFinder.findFirstByTraitTypeId(
                burnedNft1WeightlessTraits, WEIGHTLESS_TRAIT_TYPE_ID_3))
        .thenReturn(burnedNft1WeightlessTraits.get(0));
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

  private void mockGetWeightlessTraitValues() throws WeightlessTraitPickerException {
    Mockito.when(mergeRarityTraitPicker.getValue(Mockito.any()))
        .thenReturn(WEIGHTLESS_TRAIT_TYPE_VALUE_1);
    Mockito.when(overallRarityTraitPicker.getValue(Mockito.any()))
        .thenReturn(WEIGHTLESS_TRAIT_TYPE_VALUE_2);
  }

  private TraitsCreatorContext buildContext() {
    return TraitsCreatorContext.builder()
        .tokenId(TOKEN_ID)
        .seedForTraits(SEED_FOR_TRAITS)
        .weightedTraitTypes(mockWeightedTraitTypes)
        .weightlessTraitTypes(mockWeightlessTraitTypes)
        .burnedNft1(burnedNft1)
        .burnedNft2(burnedNft2)
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
    assertThat(values.get(2).getValue()).isEqualTo(BURNED_TOKEN_1_VALUE);
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
