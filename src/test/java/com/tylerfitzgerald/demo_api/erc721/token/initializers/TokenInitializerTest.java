package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

public class TokenInitializerTest {

  private InitializeTokenWeightlessTraitsCreator weightlessTraitsCreator;
  private WeightedTraitsCreator weightedTraitsCreator;
  private final Long TOKEN_ID = 11L;
  private final Long SEED_FOR_TRAITS = 123123321L;

  @BeforeEach
  public void setup() {
    weightedTraitsCreator = Mockito.mock(WeightedTraitsCreator.class);
    weightlessTraitsCreator = Mockito.mock(InitializeTokenWeightlessTraitsCreator.class);
  }

  @Test
  void testBuilder() throws TokenInitializeException {
    //    TokenInitializer tokenInitializer =
    //        new TokenInitializer(weightlessTraitsCreator, weightedTraitsCreator);
    //    tokenInitializer.initialize(TOKEN_ID, SEED_FOR_TRAITS);
  }
}
