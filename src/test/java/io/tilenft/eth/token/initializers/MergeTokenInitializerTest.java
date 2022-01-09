package io.tilenft.eth.token.initializers;

import static org.assertj.core.api.Assertions.assertThat;

import io.tilenft.config.external.TokenConfig;
import io.tilenft.etc.lists.finders.WeightedTraitTypesListFinder;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.traits.creators.TraitsCreatorContext;
import io.tilenft.eth.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;
import io.tilenft.sql.dtos.TokenDTO;
import io.tilenft.sql.dtos.WeightedTraitDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeDTO;
import io.tilenft.sql.dtos.WeightedTraitTypeWeightDTO;
import io.tilenft.sql.dtos.WeightlessTraitDTO;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import io.tilenft.sql.repositories.TokenRepository;
import io.tilenft.sql.repositories.WeightedTraitTypeRepository;
import io.tilenft.sql.repositories.WeightedTraitTypeWeightRepository;
import io.tilenft.sql.repositories.WeightlessTraitTypeRepository;
import java.util.ArrayList;
import java.util.List;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class MergeTokenInitializerTest {
  private final Long WEIGHTED_TRAIT_TYPE_ID_1 = 1L;
  private final Long WEIGHTED_TRAIT_TYPE_ID_2 = 2L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1 = 3L;
  private final Long WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2 = 4L;
  private final Long WEIGHTLESS_TRAIT_TYPE_ID_1 = 5L;
  private final Long WEIGHTLESS_TRAIT_TYPE_ID_2 = 6L;
  private final Long WEIGHTLESS_TRAIT_1 = 7L;
  private final Long WEIGHTLESS_TRAIT_2 = 8L;
  private final Long WEIGHTED_TRAIT_1 = 9L;
  private final Long WEIGHTED_TRAIT_2 = 10L;
  @Mock private TokenRepository tokenRepository;
  @Mock private TokenConfig tokenConfig;
  @Mock private WeightedTraitTypesListFinder weightedTraitTypesListFinder;
  @Mock protected WeightedTraitTypeRepository weightedTraitTypeRepository;
  @Mock private WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository;
  @Mock private WeightlessTraitTypeRepository weightlessTraitTypeRepository;
  @Mock private MergeTokenWeightlessTraitsCreator weightlessTraitsCreator;
  @Mock private WeightedTraitsCreator weightedTraitsCreator;

  @InjectMocks
  private MergeTokenInitializer mergeTokenInitializer =
      new MergeTokenInitializer(weightlessTraitsCreator, weightedTraitsCreator);

  private final Long EXISTING_TOKEN_ID_1 = 11L;
  private final Long EXISTING_TOKEN_ID_2 = 12L;
  private final Long EXISTING_TOKEN_ID_3 = 13L;
  private final Long NEW_TOKEN_ID = 14L;
  private final Long SEED_FOR_TRAITS = 123123321L;

  @Captor ArgumentCaptor<TraitsCreatorContext> createTraitsContext;

  @Test
  void testMergeInitializeExistingTokenId() throws TokenInitializeException {
    Mockito.when(tokenRepository.create(Mockito.any())).thenReturn(null);
    TokenFacadeDTO burntToken1 =
        TokenFacadeDTO.builder()
            .tokenDTO(TokenDTO.builder().tokenId(EXISTING_TOKEN_ID_1).build())
            .build();
    TokenFacadeDTO burntToken2 =
        TokenFacadeDTO.builder()
            .tokenDTO(TokenDTO.builder().tokenId(EXISTING_TOKEN_ID_2).build())
            .build();
    Assertions.assertThat(
            mergeTokenInitializer.initialize(
                EXISTING_TOKEN_ID_3, burntToken1, burntToken2, SEED_FOR_TRAITS))
        .isEqualTo(null);
  }

  @Test
  void testMergeInitializeNewTokenId() throws TokenInitializeException {
    Mockito.when(tokenRepository.create(Mockito.any())).thenReturn(null);
    TokenFacadeDTO burntToken1 =
        TokenFacadeDTO.builder()
            .tokenDTO(TokenDTO.builder().tokenId(EXISTING_TOKEN_ID_1).build())
            .build();
    TokenFacadeDTO burntToken2 =
        TokenFacadeDTO.builder()
            .tokenDTO(TokenDTO.builder().tokenId(EXISTING_TOKEN_ID_2).build())
            .build();
    TokenDTO token =
        TokenDTO.builder()
            .tokenId(NEW_TOKEN_ID)
            .saleId(1L)
            .name(tokenConfig.getBase_name() + " " + NEW_TOKEN_ID.toString())
            .description(tokenConfig.getDescription())
            .externalUrl(tokenConfig.getBase_external_url() + NEW_TOKEN_ID)
            .imageUrl(tokenConfig.getBase_image_url() + NEW_TOKEN_ID)
            .build();
    Mockito.when(tokenRepository.create(Mockito.any())).thenReturn(token);
    List<WeightedTraitTypeDTO> mockedWeightedTraitTypes = mockWeightedTraitTypes();
    List<WeightedTraitTypeDTO> filteredMockedWeightedTraitTypes =
        mockedWeightedTraitTypes.subList(0, 1);
    List<WeightedTraitTypeWeightDTO> mockedWeightedTraitTypeWeights =
        mockWeightedTraitTypeWeights();
    List<WeightlessTraitTypeDTO> mockedWeightlessTraitTypes = mockWeightlessTraitTypes();
    List<WeightedTraitDTO> mockedWeightedTraits = mockWeightedTraits();
    List<WeightlessTraitDTO> mockedWeightlessTraits = mockWeightlessTraits();
    Mockito.when(
            weightedTraitTypesListFinder.findByIgnoringTraitTypeIdList(
                mockedWeightedTraitTypes, MergeTokenInitializer.WEIGHTED_TRAIT_TYPES_TO_IGNORE))
        .thenReturn(filteredMockedWeightedTraitTypes);
    TokenFacadeDTO results =
        mergeTokenInitializer.initialize(NEW_TOKEN_ID, burntToken1, burntToken2, SEED_FOR_TRAITS);
    assertThat(results).isInstanceOf(TokenFacadeDTO.class);
    Assertions.assertThat(results.getWeightedTraits()).isEqualTo(mockedWeightedTraits);
    Assertions.assertThat(results.getWeightlessTraits()).isEqualTo(mockedWeightlessTraits);
    Mockito.verify(weightedTraitTypeRepository, Mockito.times(1)).read();
    Mockito.verify(weightedTraitTypeWeightRepository, Mockito.times(1)).read();
    Mockito.verify(weightlessTraitTypeRepository, Mockito.times(1)).read();
    Mockito.verify(weightedTraitsCreator, Mockito.times(1)).createTraits(Mockito.any());
    Mockito.verify(weightedTraitsCreator, Mockito.times(1)).getCreatedWeightedTraits();
    assertOnWeightedTraitsCreation(
        NEW_TOKEN_ID,
        SEED_FOR_TRAITS,
        filteredMockedWeightedTraitTypes,
        mockedWeightedTraitTypeWeights,
        mockedWeightlessTraitTypes);
    assertOnWeightlessTraitsCreation(
        NEW_TOKEN_ID,
        SEED_FOR_TRAITS,
        mockedWeightedTraits,
        filteredMockedWeightedTraitTypes,
        mockedWeightedTraitTypeWeights,
        mockedWeightlessTraitTypes,
        mockedWeightedTraits);
  }

  private void assertOnWeightedTraitsCreation(
      Long tokenId,
      Long seedForTraits,
      List<WeightedTraitTypeDTO> mockedWeightedTraitTypes,
      List<WeightedTraitTypeWeightDTO> mockedWeightedTraitTypeWeights,
      List<WeightlessTraitTypeDTO> mockedWeightlessTraitTypes) {
    Mockito.verify(weightedTraitsCreator, Mockito.times(1))
        .createTraits(createTraitsContext.capture());
    Assertions.assertThat(createTraitsContext.getValue().getWeightedTraits())
        .isEqualTo(new ArrayList<>());
    assertThat(createTraitsContext.getValue().getTokenId()).isEqualTo(tokenId);
    assertThat(createTraitsContext.getValue().getSeedForTraits()).isEqualTo(seedForTraits);
    Assertions.assertThat(createTraitsContext.getValue().getWeightedTraitTypes())
        .isEqualTo(mockedWeightedTraitTypes);
    Assertions.assertThat(createTraitsContext.getValue().getWeightedTraitTypeWeights())
        .isEqualTo(mockedWeightedTraitTypeWeights);
    Assertions.assertThat(createTraitsContext.getValue().getWeightlessTraitTypes())
        .isEqualTo(mockedWeightlessTraitTypes);
  }

  private void assertOnWeightlessTraitsCreation(
      Long tokenId,
      Long seedForTraits,
      List<WeightedTraitDTO> weightedTraits,
      List<WeightedTraitTypeDTO> mockedWeightedTraitTypes,
      List<WeightedTraitTypeWeightDTO> mockedWeightedTraitTypeWeights,
      List<WeightlessTraitTypeDTO> mockedWeightlessTraitTypes,
      List<WeightedTraitDTO> mockedWeightedTraits)
      throws TokenInitializeException {
    Mockito.verify(weightlessTraitsCreator, Mockito.times(1))
        .createTraits(createTraitsContext.capture());
    Assertions.assertThat(createTraitsContext.getValue().getWeightedTraits())
        .isEqualTo(mockedWeightedTraits);
    assertThat(createTraitsContext.getValue().getTokenId()).isEqualTo(tokenId);
    assertThat(createTraitsContext.getValue().getSeedForTraits()).isEqualTo(seedForTraits);
    Assertions.assertThat(createTraitsContext.getValue().getWeightedTraits())
        .isEqualTo(weightedTraits);
    Assertions.assertThat(createTraitsContext.getValue().getWeightedTraitTypes())
        .isEqualTo(mockedWeightedTraitTypes);
    Assertions.assertThat(createTraitsContext.getValue().getWeightedTraitTypeWeights())
        .isEqualTo(mockedWeightedTraitTypeWeights);
    Assertions.assertThat(createTraitsContext.getValue().getWeightlessTraitTypes())
        .isEqualTo(mockedWeightlessTraitTypes);
  }

  private List<WeightedTraitTypeDTO> mockWeightedTraitTypes() {
    List<WeightedTraitTypeDTO> weightedTraitTypes = new ArrayList<>();
    weightedTraitTypes.add(
        WeightedTraitTypeDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1).build());
    weightedTraitTypes.add(
        WeightedTraitTypeDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2).build());
    weightedTraitTypes.add(
        WeightedTraitTypeDTO.builder()
            .traitTypeId((long) MergeTokenInitializer.WEIGHTED_TRAIT_TYPES_TO_IGNORE[0])
            .build());
    Mockito.when(weightedTraitTypeRepository.read()).thenReturn(weightedTraitTypes);
    return weightedTraitTypes;
  }

  private List<WeightedTraitTypeWeightDTO> mockWeightedTraitTypeWeights() {
    List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1)
            .build());
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2)
            .build());
    Mockito.when(weightedTraitTypeWeightRepository.read()).thenReturn(weightedTraitTypeWeights);
    return weightedTraitTypeWeights;
  }

  private List<WeightlessTraitTypeDTO> mockWeightlessTraitTypes() {
    List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();
    weightlessTraitTypes.add(
        WeightlessTraitTypeDTO.builder().weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1).build());
    weightlessTraitTypes.add(
        WeightlessTraitTypeDTO.builder().weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2).build());
    Mockito.when(weightlessTraitTypeRepository.read()).thenReturn(weightlessTraitTypes);
    return weightlessTraitTypes;
  }

  private List<WeightlessTraitDTO> mockWeightlessTraits() {
    List<WeightlessTraitDTO> weightlessTraits = new ArrayList<>();
    weightlessTraits.add(
        WeightlessTraitDTO.builder()
            .traitId(WEIGHTLESS_TRAIT_1)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .build());
    weightlessTraits.add(
        WeightlessTraitDTO.builder()
            .traitId(WEIGHTLESS_TRAIT_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .build());
    Mockito.when(weightlessTraitsCreator.getCreatedWeightlessTraits()).thenReturn(weightlessTraits);
    return weightlessTraits;
  }

  private List<WeightedTraitDTO> mockWeightedTraits() {
    List<WeightedTraitDTO> weightedTraits = new ArrayList<>();
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .traitId(WEIGHTED_TRAIT_1)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1)
            .build());
    weightedTraits.add(
        WeightedTraitDTO.builder()
            .traitId(WEIGHTED_TRAIT_2)
            .traitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2)
            .build());
    Mockito.when(weightedTraitsCreator.getCreatedWeightedTraits()).thenReturn(weightedTraits);
    return weightedTraits;
  }
}
