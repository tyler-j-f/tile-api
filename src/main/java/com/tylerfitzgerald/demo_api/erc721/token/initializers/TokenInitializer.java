package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.InitializeTokenWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.WeightlessTraitsCreatorContext;

public class TokenInitializer extends AbstractTokenInitializer {

  public TokenInitializer(InitializeTokenWeightlessTraitsCreator weightlessTraitsCreator) {
    super(weightlessTraitsCreator);
  }

  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };

  public TokenFacadeDTO initialize(Long tokenId, Long seedForTraits)
      throws TokenInitializeException {
    this.seedForTraits = seedForTraits;
    tokenDTO = createToken(tokenId);
    if (tokenDTO == null) {
      System.out.println(
          "TokenInitializer failed to initialize the token with tokenId: " + tokenId);
      return null;
    }
    weightedTraitTypes =
        filterOutWeightedTraitTypesToIgnore(
            weightedTraitTypeRepository.read(), WEIGHTED_TRAIT_TYPES_TO_IGNORE);
    weightedTraitTypeWeights = weightedTraitTypeWeightRepository.read();
    weightedTraits = createWeightedTraits();
    weightlessTraitsCreator.createTraits(
        WeightlessTraitsCreatorContext.builder()
            .tokenId(tokenId)
            .seedForTraits(seedForTraits)
            .weightedTraits(weightedTraits)
            .weightedTraitTypes(weightedTraitTypes)
            .weightedTraitTypeWeights(weightedTraitTypeWeights)
            .build());
    return buildTokenFacadeDTO();
  }
}
