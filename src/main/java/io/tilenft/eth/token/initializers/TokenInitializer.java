package io.tilenft.eth.token.initializers;

import io.tilenft.etc.lists.finders.WeightlessTraitTypesListFinder;
import io.tilenft.eth.token.TokenFacadeDTO;
import io.tilenft.eth.token.traits.creators.TraitsCreatorContext;
import io.tilenft.eth.token.traits.creators.weighted.WeightedTraitsCreator;
import io.tilenft.eth.token.traits.creators.weightless.InitializeTokenWeightlessTraitsCreator;
import io.tilenft.eth.token.traits.weighted.WeightedTraitTypeConstants;
import io.tilenft.eth.token.traits.weightless.WeightlessTraitTypeConstants;
import io.tilenft.sql.dtos.WeightlessTraitTypeDTO;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;

public class TokenInitializer extends AbstractTokenInitializer {

  @Autowired private WeightlessTraitTypesListFinder weightlessTraitTypesListFinder;

  public TokenInitializer(
      InitializeTokenWeightlessTraitsCreator weightlessTraitsCreator,
      WeightedTraitsCreator weightedTraitsCreator) {
    super(weightlessTraitsCreator, weightedTraitsCreator);
  }

  public static final int[] WEIGHTLESS_TRAIT_TYPES_TO_IGNORE = {
    WeightlessTraitTypeConstants.TILE_1_RARITY,
    WeightlessTraitTypeConstants.TILE_2_RARITY,
    WeightlessTraitTypeConstants.TILE_3_RARITY,
    WeightlessTraitTypeConstants.TILE_4_RARITY
  };
  public static final int[] WEIGHTED_TRAIT_TYPES_TO_IGNORE = {
    WeightedTraitTypeConstants.IS_BURNT_TOKEN_EQUALS_TRUE
  };

  public TokenFacadeDTO initialize(Long tokenId, Long seedForTraits)
      throws TokenInitializeException {
    resetLists();
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
    weightlessTraitTypes = weightlessTraitTypeRepository.read();
    List<WeightlessTraitTypeDTO> filteredWeightlessTraitTypes =
        weightlessTraitTypesListFinder.findByIgnoringTraitTypeIdList(
            weightlessTraitTypes, WEIGHTLESS_TRAIT_TYPES_TO_IGNORE);
    weightedTraitsCreator.createTraits(
        getContext(tokenId, seedForTraits, filteredWeightlessTraitTypes));
    weightedTraits = weightedTraitsCreator.getCreatedWeightedTraits();
    weightlessTraitsCreator.createTraits(
        getContext(tokenId, seedForTraits, filteredWeightlessTraitTypes));
    return buildTokenFacadeDTO();
  }

  private TraitsCreatorContext getContext(
      Long tokenId, Long seedForTraits, List<WeightlessTraitTypeDTO> filteredWeightlessTraitTypes) {
    return TraitsCreatorContext.builder()
        .tokenId(tokenId)
        .seedForTraits(seedForTraits)
        .weightlessTraitTypes(filteredWeightlessTraitTypes)
        .weightedTraits(weightedTraits)
        .weightedTraitTypes(weightedTraitTypes)
        .weightedTraitTypeWeights(weightedTraitTypeWeights)
        .isDryRun(false)
        .build();
  }

  private void resetLists() {
    weightedTraits = new ArrayList<>();
    weightedTraitTypes = new ArrayList<>();
    weightedTraitTypeWeights = new ArrayList<>();
    weightlessTraitTypes = new ArrayList<>();
  }
}
