package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightlessTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;
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
public class TokenInitializerTest {
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
  @Mock private InitializeTokenWeightlessTraitsCreator weightlessTraitsCreator;
  @Mock private WeightedTraitsCreator weightedTraitsCreator;
  @Mock private WeightlessTraitTypesListFinder weightlessTraitTypesListFinder;

  @InjectMocks
  private TokenInitializer tokenInitializer =
      new TokenInitializer(weightlessTraitsCreator, weightedTraitsCreator);

  private final Long EXISTING_TOKEN_ID = 11L;
  private final Long NEW_TOKEN_ID = 12L;
  private final Long SEED_FOR_TRAITS = 123123321L;

  @Captor ArgumentCaptor<TraitsCreatorContext> createTraitsContextCaptor;

  @Test
  void testInitializeExistingTokenId() throws TokenInitializeException {
    Mockito.when(tokenRepository.create(Mockito.any())).thenReturn(null);
    assertThat(tokenInitializer.initialize(EXISTING_TOKEN_ID, SEED_FOR_TRAITS)).isEqualTo(null);
  }

  @Test
  void testInitializeNewTokenId() throws TokenInitializeException {
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
    List<WeightlessTraitTypeDTO> filteredMockedWeightlessTraitTypes =
        mockedWeightlessTraitTypes.subList(0, 1);
    List<WeightedTraitDTO> mockedWeightedTraits = mockWeightedTraits();
    List<WeightlessTraitDTO> mockedWeightlessTraits = mockWeightlessTraits();
    Mockito.when(
            weightedTraitTypesListFinder.findByIgnoringTraitTypeIdList(
                mockedWeightedTraitTypes, TokenInitializer.WEIGHTED_TRAIT_TYPES_TO_IGNORE))
        .thenReturn(filteredMockedWeightedTraitTypes);
    Mockito.when(
            weightlessTraitTypesListFinder.findByIgnoringTraitTypeIdList(
                mockedWeightlessTraitTypes, TokenInitializer.WEIGHTLESS_TRAIT_TYPES_TO_IGNORE))
        .thenReturn(filteredMockedWeightlessTraitTypes);
    TokenFacadeDTO results = tokenInitializer.initialize(NEW_TOKEN_ID, SEED_FOR_TRAITS);
    assertThat(results).isInstanceOf(TokenFacadeDTO.class);
    assertThat(results.getWeightedTraits()).isEqualTo(mockedWeightedTraits);
    assertThat(results.getWeightlessTraits()).isEqualTo(mockedWeightlessTraits);
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
        filteredMockedWeightlessTraitTypes);
    assertOnWeightlessTraitsCreation(
        NEW_TOKEN_ID,
        SEED_FOR_TRAITS,
        mockedWeightedTraits,
        filteredMockedWeightedTraitTypes,
        mockedWeightedTraitTypeWeights,
        filteredMockedWeightlessTraitTypes,
        mockedWeightedTraits);
  }

  private void assertOnWeightedTraitsCreation(
      Long tokenId,
      Long seedForTraits,
      List<WeightedTraitTypeDTO> mockedWeightedTraitTypes,
      List<WeightedTraitTypeWeightDTO> mockedWeightedTraitTypeWeights,
      List<WeightlessTraitTypeDTO> mockedWeightlessTraitTypes) {
    Mockito.verify(weightedTraitsCreator, Mockito.times(1))
        .createTraits(createTraitsContextCaptor.capture());
    assertThat(createTraitsContextCaptor.getValue().getWeightedTraits())
        .isEqualTo(new ArrayList<>());
    assertThat(createTraitsContextCaptor.getValue().getTokenId()).isEqualTo(tokenId);
    assertThat(createTraitsContextCaptor.getValue().getSeedForTraits()).isEqualTo(seedForTraits);
    assertThat(createTraitsContextCaptor.getValue().getWeightedTraitTypes())
        .isEqualTo(mockedWeightedTraitTypes);
    assertThat(createTraitsContextCaptor.getValue().getWeightedTraitTypeWeights())
        .isEqualTo(mockedWeightedTraitTypeWeights);
    assertThat(createTraitsContextCaptor.getValue().getWeightlessTraitTypes())
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
        .createTraits(createTraitsContextCaptor.capture());
    assertThat(createTraitsContextCaptor.getValue().getWeightedTraits())
        .isEqualTo(mockedWeightedTraits);
    assertThat(createTraitsContextCaptor.getValue().getTokenId()).isEqualTo(tokenId);
    assertThat(createTraitsContextCaptor.getValue().getSeedForTraits()).isEqualTo(seedForTraits);
    assertThat(createTraitsContextCaptor.getValue().getWeightedTraits()).isEqualTo(weightedTraits);
    assertThat(createTraitsContextCaptor.getValue().getWeightedTraitTypes())
        .isEqualTo(mockedWeightedTraitTypes);
    assertThat(createTraitsContextCaptor.getValue().getWeightedTraitTypeWeights())
        .isEqualTo(mockedWeightedTraitTypeWeights);
    assertThat(createTraitsContextCaptor.getValue().getWeightlessTraitTypes())
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
            .traitTypeId((long) TokenInitializer.WEIGHTED_TRAIT_TYPES_TO_IGNORE[0])
            .build());
    Mockito.when(weightedTraitTypeRepository.read()).thenReturn(weightedTraitTypes);
    return weightedTraitTypes;
  }

  private List<WeightedTraitTypeWeightDTO> mockWeightedTraitTypeWeights() {
    List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .build());
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .build());
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId((long) TokenInitializer.WEIGHTLESS_TRAIT_TYPES_TO_IGNORE[0])
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
