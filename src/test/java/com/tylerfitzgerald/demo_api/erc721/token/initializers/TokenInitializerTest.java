package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import static org.assertj.core.api.Assertions.assertThat;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.etc.lsitFinders.WeightedTraitTypesListFinder;
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

  private Long EXISTING_TOKEN_ID = 1L;

  @InjectMocks
  private TokenInitializer tokenInitializer =
      new TokenInitializer(weightlessTraitsCreator, weightedTraitsCreator);

  private final Long TOKEN_ID = 11L;
  private final Long SEED_FOR_TRAITS = 123123321L;

  @Test
  void testInitializeExisting() throws TokenInitializeException {
    Mockito.when(tokenRepository.create(Mockito.any())).thenReturn(null);
    assertThat(tokenInitializer.initialize(TOKEN_ID, SEED_FOR_TRAITS)).isEqualTo(null);
  }
}
