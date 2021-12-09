package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.metadata.TokenMetadataDTO;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacade;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.AbstractWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.WeightlessTraitsCreatorContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class MergeTokenInitializer extends AbstractTokenInitializer {

  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.TILE_1_RARITY,
    WeightedTraitTypeConstants.TILE_2_RARITY,
    WeightedTraitTypeConstants.TILE_3_RARITY,
    WeightedTraitTypeConstants.TILE_4_RARITY,
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };

  @Autowired private TokenFacade tokenFacade;

  @Qualifier("mergeTokenWeightlessTraitsCreator")
  @Autowired
  protected AbstractWeightlessTraitsCreator weightlessTraitsCreator;

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
    weightedTraits = createWeightedTraits();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    weightlessTraitsCreator.createTraits(
        WeightlessTraitsCreatorContext.builder()
            .tokenId(tokenId)
            .seedForTraits(seedForTraits)
            .weightlessTraitTypes(weightlessTraitTypes)
            .weightedTraits(weightedTraits)
            .weightedTraitTypes(weightedTraitTypes)
            .weightedTraitTypeWeights(weightedTraitTypeWeights)
            .burnedNft1(burnedNft1)
            .burnedNft2(burnedNft2)
            .build());
    return tokenFacade
        .setTokenFacadeDTO(buildTokenFacadeDTO(weightlessTraitsCreator))
        .buildTokenMetadataDTO();
  }
}
