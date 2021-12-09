package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.AbstractWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypeWeightsFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypesFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitTypesFinder;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;

public class TokenInitializer extends AbstractTokenInitializer {

  public TokenInitializer(
      TokenRepository tokenRepository,
      TokenConfig tokenConfig,
      WeightedTraitRepository weightedTraitRepository,
      WeightedTraitTypesFinder weightedTraitTypesFinder,
      WeightlessTraitTypesFinder weightlessTraitTypesFinder,
      WeightedTraitTypeRepository weightedTraitTypeRepository,
      WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository,
      WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder,
      WeightlessTraitTypeRepository weightlessTraitTypeRepository,
      AbstractWeightlessTraitsCreator weightlessTraitsCreator) {
    super(
        tokenRepository,
        tokenConfig,
        weightedTraitRepository,
        weightedTraitTypesFinder,
        weightlessTraitTypesFinder,
        weightedTraitTypeRepository,
        weightedTraitTypeWeightRepository,
        weightedTraitTypeWeightsFinder,
        weightlessTraitTypeRepository,
        weightlessTraitsCreator);
  }

  private static final int[] WEIGHTLESS_TRAIT_TYPES_TO_IGNORE = {
    WeightlessTraitTypeConstants.TILE_1_RARITY,
    WeightlessTraitTypeConstants.TILE_2_RARITY,
    WeightlessTraitTypeConstants.TILE_3_RARITY,
    WeightlessTraitTypeConstants.TILE_4_RARITY
  };
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
    weightlessTraitTypes =
        weightlessTraitTypesFinder.findByIgnoringTraitTypeIdList(
            weightlessTraitTypeRepository.read(), WEIGHTLESS_TRAIT_TYPES_TO_IGNORE);
    weightlessTraitsCreator.createTraits(
        TraitsCreatorContext.builder()
            .tokenId(tokenId)
            .weightlessTraitTypes(weightlessTraitTypes)
            .seedForTraits(seedForTraits)
            .weightedTraits(weightedTraits)
            .weightedTraitTypes(weightedTraitTypes)
            .weightedTraitTypeWeights(weightedTraitTypeWeights)
            .build());
    return buildTokenFacadeDTO();
  }
}
