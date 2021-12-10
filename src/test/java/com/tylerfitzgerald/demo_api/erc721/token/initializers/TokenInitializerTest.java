package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightlessTraitTypesListFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.TokenDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class TokenInitializerTest {
  @Mock private TokenRepository tokenRepository;
  @Mock private TokenConfig tokenConfig;
  @Mock private WeightedTraitTypesListFinder weightedTraitTypesListFinder;
  @Mock private WeightedTraitTypeRepository weightedTraitTypeRepository;
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
    assertThat(tokenInitializer.initialize(NEW_TOKEN_ID, SEED_FOR_TRAITS))
        .isInstanceOf(TokenFacadeDTO.class);
  }
}
