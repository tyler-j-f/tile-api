package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.metadata.TokenMetadataDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.AbstractWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypeWeightsFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypesFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitTypesFinder;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;

public class MergeTokenInitializer extends AbstractTokenInitializer {

  private TokenFacade tokenFacade;

  public MergeTokenInitializer(
      TokenRepository tokenRepository,
      TokenConfig tokenConfig,
      WeightedTraitRepository weightedTraitRepository,
      WeightedTraitTypesFinder weightedTraitTypesFinder,
      WeightlessTraitTypesFinder weightlessTraitTypesFinder,
      WeightedTraitTypeRepository weightedTraitTypeRepository,
      WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository,
      WeightedTraitTypeWeightsFinder weightedTraitTypeWeightsFinder,
      WeightlessTraitTypeRepository weightlessTraitTypeRepository,
      AbstractWeightlessTraitsCreator weightlessTraitsCreator,
      WeightedTraitsCreator weightedTraitsCreator,
      TokenFacade tokenFacade) {
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
        weightlessTraitsCreator,
        weightedTraitsCreator);
    this.tokenFacade = tokenFacade;
  }

  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.TILE_1_RARITY,
    WeightedTraitTypeConstants.TILE_2_RARITY,
    WeightedTraitTypeConstants.TILE_3_RARITY,
    WeightedTraitTypeConstants.TILE_4_RARITY,
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };

  public TokenMetadataDTO initialize(
      Long tokenId, TokenFacadeDTO burnedNft1, TokenFacadeDTO burnedNft2, Long seedForTraits)
      throws TokenInitializeException {
    this.seedForTraits = seedForTraits;
    if (burnedNft1 == null) {
      System.out.println(
          "TokenInitializer failed to load burned token 1. burnedNft1: " + burnedNft1);
      return null;
    }
    if (burnedNft2 == null) {
      System.out.println(
          "TokenInitializer failed to load burned token 2. burnedNft2: " + burnedNft2);
      return null;
    }
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
    weightedTraitsCreator.createTraits(
        TraitsCreatorContext.builder()
            .tokenId(tokenId)
            .seedForTraits(seedForTraits)
            .weightlessTraitTypes(weightlessTraitTypes)
            .weightedTraits(weightedTraits)
            .weightedTraitTypes(weightedTraitTypes)
            .weightedTraitTypeWeights(weightedTraitTypeWeights)
            .burnedNft1(burnedNft1)
            .burnedNft2(burnedNft2)
            .build());
    weightedTraits = weightedTraitsCreator.getCreatedWeightedTraits();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    weightlessTraitsCreator.createTraits(
        TraitsCreatorContext.builder()
            .tokenId(tokenId)
            .seedForTraits(seedForTraits)
            .weightlessTraitTypes(weightlessTraitTypes)
            .weightedTraits(weightedTraits)
            .weightedTraitTypes(weightedTraitTypes)
            .weightedTraitTypeWeights(weightedTraitTypeWeights)
            .burnedNft1(burnedNft1)
            .burnedNft2(burnedNft2)
            .build());
    return tokenFacade.setTokenFacadeDTO(buildTokenFacadeDTO()).buildTokenMetadataDTO();
  }
}
