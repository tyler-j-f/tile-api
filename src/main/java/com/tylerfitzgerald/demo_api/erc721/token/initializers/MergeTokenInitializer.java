package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.MergeTokenWeightlessTraitsCreator;

public class MergeTokenInitializer extends AbstractTokenInitializer {

  public MergeTokenInitializer(
      MergeTokenWeightlessTraitsCreator weightlessTraitsCreator,
      WeightedTraitsCreator weightedTraitsCreator) {
    super(weightlessTraitsCreator, weightedTraitsCreator);
  }

  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.TILE_1_RARITY,
    WeightedTraitTypeConstants.TILE_2_RARITY,
    WeightedTraitTypeConstants.TILE_3_RARITY,
    WeightedTraitTypeConstants.TILE_4_RARITY,
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };

  public TokenFacadeDTO initialize(
      Long tokenId, TokenFacadeDTO burnedNft1, TokenFacadeDTO burnedNft2, Long seedForTraits)
      throws TokenInitializeException {
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
    weightedTraitTypes = weightedTraitTypeRepository.read();
    weightedTraitTypeWeights = weightedTraitTypeWeightRepository.read();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    TraitsCreatorContext context =
        TraitsCreatorContext.builder()
            .tokenId(tokenId)
            .seedForTraits(seedForTraits)
            .weightlessTraitTypes(weightlessTraitTypes)
            .weightedTraits(weightedTraits)
            .weightedTraitTypes(
                filterOutWeightedTraitTypesToIgnore(
                    weightedTraitTypes, WEIGHTED_TRAIT_TYPES_TO_IGNORE))
            .weightedTraitTypeWeights(weightedTraitTypeWeights)
            .burnedNft1(burnedNft1)
            .burnedNft2(burnedNft2)
            .build();
    weightedTraitsCreator.createTraits(context);
    weightedTraits = weightedTraitsCreator.getCreatedWeightedTraits();
    weightlessTraitsCreator.createTraits(context);
    return buildTokenFacadeDTO();
  }
}
