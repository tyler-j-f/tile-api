package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.AbstractWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitTypesFinder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

public class TokenInitializer extends AbstractTokenInitializer {

  private static final int[] WEIGHTLESS_TRAIT_TYPES_TO_IGNORE = {
    WeightlessTraitTypeConstants.TILE_1_RARITY,
    WeightlessTraitTypeConstants.TILE_2_RARITY,
    WeightlessTraitTypeConstants.TILE_3_RARITY,
    WeightlessTraitTypeConstants.TILE_4_RARITY
  };
  private static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };
  @Autowired private WeightlessTraitTypesFinder weightlessTraitTypesFinder;

  @Qualifier("initializeTokenWeightlessTraitsCreator")
  @Autowired
  protected AbstractWeightlessTraitsCreator weightlessTraitsCreator;

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
    return buildTokenFacadeDTO(weightlessTraitsCreator);
  }
}
