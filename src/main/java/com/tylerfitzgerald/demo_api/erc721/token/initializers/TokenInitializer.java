package com.tylerfitzgerald.demo_api.erc721.token.initializers;

import com.tylerfitzgerald.demo_api.config.external.TokenConfig;
import com.tylerfitzgerald.demo_api.erc721.token.TokenFacadeDTO;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightedTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.WeightlessTraitTypeConstants;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.TraitsCreatorContext;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weighted.WeightedTraitsCreator;
import com.tylerfitzgerald.demo_api.erc721.token.traits.creators.weightless.AbstractWeightlessTraitsCreator;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightedTraitTypesFinder;
import com.tylerfitzgerald.demo_api.etc.listFinders.WeightlessTraitTypesFinder;
import com.tylerfitzgerald.demo_api.sql.dtos.WeightlessTraitTypeDTO;
import com.tylerfitzgerald.demo_api.sql.repositories.TokenRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightedTraitTypeWeightRepository;
import com.tylerfitzgerald.demo_api.sql.repositories.WeightlessTraitTypeRepository;
import java.util.List;

public class TokenInitializer extends AbstractTokenInitializer {

  private WeightlessTraitTypesFinder weightlessTraitTypesFinder;

  public TokenInitializer(
      TokenRepository tokenRepository,
      TokenConfig tokenConfig,
      WeightedTraitTypesFinder weightedTraitTypesFinder,
      WeightedTraitTypeRepository weightedTraitTypeRepository,
      WeightedTraitTypeWeightRepository weightedTraitTypeWeightRepository,
      WeightlessTraitTypeRepository weightlessTraitTypeRepository,
      AbstractWeightlessTraitsCreator weightlessTraitsCreator,
      WeightedTraitsCreator weightedTraitsCreator,
      WeightlessTraitTypesFinder weightlessTraitTypesFinder) {
    super(
        tokenRepository,
        tokenConfig,
        weightedTraitTypesFinder,
        weightedTraitTypeRepository,
        weightedTraitTypeWeightRepository,
        weightlessTraitTypeRepository,
        weightlessTraitsCreator,
        weightedTraitsCreator);
    this.weightlessTraitTypesFinder = weightlessTraitTypesFinder;
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
    tokenDTO = createToken(tokenId);
    if (tokenDTO == null) {
      System.out.println(
          "TokenInitializer failed to initialize the token with tokenId: " + tokenId);
      return null;
    }
    weightedTraitTypes = weightedTraitTypeRepository.read();
    weightedTraitTypeWeights = weightedTraitTypeWeightRepository.read();
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    List<WeightlessTraitTypeDTO> filteredWeightlessTraitTypes =
        weightlessTraitTypesFinder.findByIgnoringTraitTypeIdList(
            weightlessTraitTypeRepository.read(), WEIGHTLESS_TRAIT_TYPES_TO_IGNORE);
    weightedTraits = weightedTraitsCreator.getCreatedWeightedTraits();
    TraitsCreatorContext context =
        TraitsCreatorContext.builder()
            .tokenId(tokenId)
            .weightlessTraitTypes(filteredWeightlessTraitTypes)
            .seedForTraits(seedForTraits)
            .weightedTraits(weightedTraits)
            .weightedTraitTypes(
                filterOutWeightedTraitTypesToIgnore(
                    weightedTraitTypes, WEIGHTED_TRAIT_TYPES_TO_IGNORE))
            .weightedTraitTypeWeights(weightedTraitTypeWeights)
            .build();
    weightedTraitsCreator.createTraits(context);
    weightlessTraitsCreator.createTraits(context);
    return buildTokenFacadeDTO();
  }
}
