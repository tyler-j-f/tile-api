package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightlessTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightedTraitTypeWeightDTO;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.Test;
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
    mockWeightedTraitTypes();
    mockWeightedTraitTypeWeights();
    mockWeightlessTraitTypes();
    TokenFacadeDTO tokenFacadeDTO = tokenInitializer.initialize(NEW_TOKEN_ID, SEED_FOR_TRAITS);
    assertThat(tokenFacadeDTO).isInstanceOf(TokenFacadeDTO.class);
    assertThat(tokenFacadeDTO.getWeightedTraits()).isEqualTo(new ArrayList<>());
    Mockito.verify(weightedTraitTypeRepository, Mockito.times(1)).read();
    Mockito.verify(weightedTraitTypeWeightRepository, Mockito.times(1)).read();
    Mockito.verify(weightlessTraitTypeRepository, Mockito.times(1)).read();
    Mockito.verify(weightedTraitsCreator, Mockito.times(1)).createTraits(Mockito.any());
    Mockito.verify(weightedTraitsCreator, Mockito.times(1)).getCreatedWeightedTraits();
    Mockito.verify(weightlessTraitsCreator, Mockito.times(1)).createTraits(Mockito.any());
  }

  private void mockWeightedTraitTypes() {
    List<WeightedTraitTypeDTO> weightedTraitTypes = new ArrayList<>();
    weightedTraitTypes.add(
        WeightedTraitTypeDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_ID_1).build());
    weightedTraitTypes.add(
        WeightedTraitTypeDTO.builder().traitTypeId(WEIGHTED_TRAIT_TYPE_ID_2).build());
    Mockito.when(weightedTraitTypeRepository.read()).thenReturn(weightedTraitTypes);
  }

  private void mockWeightedTraitTypeWeights() {
    List<WeightedTraitTypeWeightDTO> weightedTraitTypeWeights = new ArrayList<>();
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_1)
            .build());
    weightedTraitTypeWeights.add(
        WeightedTraitTypeWeightDTO.builder()
            .traitTypeWeightId(WEIGHTED_TRAIT_TYPE_WEIGHT_ID_2)
            .build());
    Mockito.when(weightedTraitTypeWeightRepository.read()).thenReturn(weightedTraitTypeWeights);
  }

  private void mockWeightlessTraitTypes() {
    List<WeightlessTraitTypeDTO> weightlessTraitTypes = new ArrayList<>();
    weightlessTraitTypes.add(
        WeightlessTraitTypeDTO.builder().weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_1).build());
    weightlessTraitTypes.add(
        WeightlessTraitTypeDTO.builder().weightlessTraitTypeId(WEIGHTLESS_TRAIT_TYPE_ID_2).build());
    Mockito.when(weightlessTraitTypeRepository.read()).thenReturn(weightlessTraitTypes);
  }
}
